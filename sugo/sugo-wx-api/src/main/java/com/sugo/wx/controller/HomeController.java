package com.sugo.wx.controller;

import com.sugo.common.system.SystemConfig;
import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.SugoCategory;
import com.sugo.sql.entity.SugoGoods;
import com.sugo.sql.service.CouponService;
import com.sugo.sql.service.GoodsService;
import com.sugo.sql.service.AdService;
import com.sugo.sql.service.CategoryService;
import com.sugo.sql.service.TopicService;
import com.sugo.wx.annotation.LoginUser;
import com.sugo.wx.service.HomeCacheManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping("/wx/home")
@Validated
public class HomeController {
    private final Log logger = LogFactory.getLog(HomeController.class);

    @Autowired
    private AdService adService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryService categoryService;

    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(9, 9, 1000, TimeUnit.MILLISECONDS, WORK_QUEUE, HANDLER);

    @GetMapping("/cache")
    public Object cache(@NotNull String key) {
        if (!key.equals("sugo_cache")) {
            return ResponseUtil.fail();
        }

        // 清除缓存
        HomeCacheManager.clearAll();
        return ResponseUtil.ok("缓存已清除");
    }

    /**
     * 首页数据
     * @param userId 当用户已经登录时，非空。为登录状态为null
     * @return 首页数据
     */
    @GetMapping("/index")
    public Object index(@LoginUser Integer userId, String adcode) {

        int code = Integer.parseInt(adcode);
        //优先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.INDEX)) {
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.INDEX));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Callable<List> bannerListCallable = () -> adService.queryIndex();

        Callable<List> channelListCallable = () -> categoryService.queryChannel();

        // 修改行政编码后2位为00，即去掉县区，保留市级 如：440111 -> 440100
        int code_01 = code / 100 * 100;

        Callable<List> newGoodsListCallable = () -> goodsService.queryByNew(0, SystemConfig.getNewLimit() / 2, code);
        Callable<List> newGoodsListCallable_01 = () -> goodsService.queryByNew(0, SystemConfig.getNewLimit() / 2, code_01);

        FutureTask<List> bannerTask = new FutureTask<>(bannerListCallable);
        FutureTask<List> channelTask = new FutureTask<>(channelListCallable);
        FutureTask<List> newGoodsListTask = new FutureTask<>(newGoodsListCallable);
        FutureTask<List> newGoodsListTask_01 = new FutureTask<>(newGoodsListCallable_01);


        executorService.submit(bannerTask);
        executorService.submit(channelTask);

        executorService.submit(newGoodsListTask);
        executorService.submit(newGoodsListTask_01);

        Map<String, Object> entity = new HashMap<>();
        List<SugoGoods> goodsList = null;
        try {
            goodsList = newGoodsListTask.get();
            goodsList.addAll(newGoodsListTask_01.get());
            entity.put("banner", bannerTask.get());
            entity.put("channel", channelTask.get());
            entity.put("newGoodsList", goodsList);
            entity.put("brandList", "");
            //缓存数据
            HomeCacheManager.loadData(HomeCacheManager.INDEX, entity);
        } catch (Exception e) {
            logger.debug(this.getClass().getName() + " error: ", e);
        }finally {
            executorService.shutdown();
        }
        return ResponseUtil.ok(entity);
    }

    private List<Map> getCategoryList() {
        List<Map> categoryList = new ArrayList<>();
        List<SugoCategory> catL1List = categoryService.queryL1WithoutRecommend(0, SystemConfig.getCatlogListLimit());
        for (SugoCategory catL1 : catL1List) {
            List<SugoCategory> catL2List = categoryService.queryByPid(catL1.getId());
            List<Integer> l2List = new ArrayList<>();
            for (SugoCategory catL2 : catL2List) {
                l2List.add(catL2.getId());
            }

            List<SugoGoods> categoryGoods;
            if (l2List.size() == 0) {
                categoryGoods = new ArrayList<>();
            } else {
                categoryGoods = goodsService.queryByCategory(l2List, 0, SystemConfig.getCatlogMoreLimit());
            }

            Map<String, Object> catGoods = new HashMap<>();
            catGoods.put("id", catL1.getId());
            catGoods.put("name", catL1.getName());
            catGoods.put("goodsList", categoryGoods);
            categoryList.add(catGoods);
        }
        return categoryList;
    }

    /**
     * 商城介绍信息
     * @return 商城介绍信息
     */
    @GetMapping("/about")
    public Object about() {
        Map<String, Object> about = new HashMap<>();
        about.put("name", SystemConfig.getMallName());
        about.put("address", SystemConfig.getMallAddress());
        about.put("phone", SystemConfig.getMallPhone());
        about.put("qq", SystemConfig.getMallQQ());
        about.put("longitude", SystemConfig.getMallLongitude());
        about.put("latitude", SystemConfig.getMallLatitude());
        return ResponseUtil.ok(about);
    }

    /**
     * 获取新分页商品数据
     * @return
     */
    @GetMapping("newGoodsList")
    public Object newGoodsList(String adcode, Integer currentPage) {
        int adcode_ = Integer.parseInt(adcode);
        int code = adcode_ / 100 * 100;

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Callable<List> goodsListCallable_01 = () -> goodsService.queryByNew(currentPage, SystemConfig.getNewLimit() / 2, code);
        Callable<List> goodsListCallable_02 = () -> goodsService.queryByNew(currentPage, SystemConfig.getNewLimit() / 2, adcode_);

        FutureTask<List> goodsListTask_01 = new FutureTask<>(goodsListCallable_01);
        FutureTask<List> goodsListTask_02 = new FutureTask<>(goodsListCallable_02);

        executorService.submit(goodsListTask_01);
        executorService.submit(goodsListTask_02);

        Map<String, Object> entity = new HashMap<>();
        List<SugoGoods> goodsList = null;
        try {
            goodsList = goodsListTask_01.get();
            goodsList.addAll(goodsListTask_02.get());
            entity.put("newGoodsList", goodsList);
        } catch (Exception e) {
            logger.debug(this.getClass().getName() + " error: ", e);
        } finally {
            executorService.shutdown();
        }

        entity.put("newGoodsList", goodsList);

        return ResponseUtil.ok(entity);
    }
}