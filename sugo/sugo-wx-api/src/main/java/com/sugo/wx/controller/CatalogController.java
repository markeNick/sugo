package com.sugo.wx.controller;

import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.SugoCategory;
import com.sugo.sql.service.CategoryService;
import com.sugo.wx.service.HomeCacheManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/catalog")
@Validated
public class CatalogController {
    private final Log logger = LogFactory.getLog(CatalogController.class);

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有一级类目
     * @return
     */
    @GetMapping("/getfirstcategory")
    public Object getFirstCategory() {
        List<SugoCategory> list = categoryService.queryL1();
        return ResponseUtil.ok(list);
    }

    /**
     * 查询一级类目下的所有二级类目
     * @param id
     * @return
     */
    @GetMapping("/getsecondcategory")
    public Object getSecondCategory(@NotNull Integer id) {
        List<SugoCategory> list = categoryService.queryByPid(id);
        return ResponseUtil.ok(list);
    }

    /**
     * 分类页面
     *      第一次进入分类页面，需要默认指定第一个一级分类目录
     *
     * @param id    分类类目ID，空值表示第一次进入该页面
     *              不为空值表示用户在选择一级分类目录
     * @return
     */
    @GetMapping("index")
    public Object index(Integer id) {
        // 查询所有一级分类目录
        List<SugoCategory> categoryList = categoryService.queryL1();


        // 当前一级分类目录
        SugoCategory currentCategory = null;

        if (id != null) {
            currentCategory = categoryService.findById(id);
        } else {
            currentCategory = categoryList.get(0);
        }

        List<SugoCategory> currentSubCategory = null;
        if (currentSubCategory != null) {
            currentSubCategory = categoryService.queryByPid(currentCategory.getId());
        }

        Map<String,Object> data = new HashMap<>();
        data.put("categoryList", categoryList);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        return ResponseUtil.ok(data);
    }

    /**
     * 所有分类数据
     * @return
     */
    @GetMapping("all")
    public Object queryAll() {
        // 先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.CATALOG)) {
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.CATALOG));
        }

        List<SugoCategory> categoryList = categoryService.queryL1();

        // 存放当前一级分类目录对应的所有二级分类目录
        Map<Integer, List<SugoCategory>> allList = new HashMap<>();

        for (SugoCategory category : categoryList) {
            List<SugoCategory> sub = categoryService.queryByPid(category.getId());
            allList.put(category.getId(), sub);
        }

        // 当前一级分类目录
        SugoCategory currentCategory = categoryList.get(0);

        // 当前一级分类目录对应的二级分类目录
        List<SugoCategory> currentSubCategory = null;

        if (currentCategory != null) {
            currentSubCategory = categoryService.queryByPid(currentCategory.getId());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("categoryList", categoryList);
        data.put("allList", allList);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);

        // 缓存数据
        HomeCacheManager.loadData(HomeCacheManager.CATALOG, data);

        return ResponseUtil.ok(data);
    }

    /**
     * 当前类目下的所有子类目
     * @param id
     * @return
     */
    @GetMapping("current")
    public Object current(@NotNull Integer id) {
        SugoCategory currentCategory = categoryService.findById(id);
        if(currentCategory == null) {
            return ResponseUtil.badArgumentValue();
        }

        List<SugoCategory> currentSubCategory = categoryService.queryByPid(currentCategory.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);

        return ResponseUtil.ok(data);
    }
}