package com.sugo.wx.controller;

import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.SugoKeyword;
import com.sugo.sql.entity.SugoSearchHistory;
import com.sugo.sql.service.KeywordService;
import com.sugo.sql.service.SearchHistoryService;
import com.sugo.wx.annotation.LoginUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索
 *      这里使用关键字匹配或者模糊查询
 *      最好使用搜索服务器比如ES
 */
@RestController
@RequestMapping("/wx/search")
@Validated
public class SearchController {
    private final Log logger = LogFactory.getLog(SearchController.class);
    @Autowired
    private KeywordService keywordsService;
    @Autowired
    private SearchHistoryService searchHistoryService;

    /**
     * 搜索页面
     *      如果用户登录了，则给出历史搜索记录
     *      如果用户没有登录，则历史记录给空
     * @param userId
     * @return
     */
    @GetMapping("index")
    public Object index(@LoginUser Integer userId) {
        // 输入框默认输入的关键词
        SugoKeyword defaultKeyword = keywordsService.queryDefault();

        // 取出热搜关键词
        List<SugoKeyword> hotKeywordList = keywordsService.queryHots();

        List<SugoSearchHistory> searchHistoryList = null;
        if (userId != null) {
            searchHistoryList = searchHistoryService.queryByUid(userId);
        } else {
            searchHistoryList = new ArrayList<>(0);
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("defaultKeyword", defaultKeyword);
        data.put("historyKeywordList", searchHistoryList);
        data.put("hotKeywordList", hotKeywordList);
        return ResponseUtil.ok(data);
    }

    /**
     * 用户输入关键字一部分时，可以推荐系统中合适的关键字
     *
     * @param keyword
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("help")
    public Object help(@NotEmpty String keyword,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit) {

        List<SugoKeyword> keywordList = keywordsService.queryByKeyword(keyword, page, limit);
        String[] keys = new String[keywordList.size()];
        int index = 0;
        for (SugoKeyword key : keywordList) {
            keys[index++] = key.getKeyword();
        }

        return ResponseUtil.ok(keys);
    }

    /**
     * 清空搜索记录
     * @param userId
     * @return
     */
    @PostMapping("clearhistory")
    public Object clearhistory(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        searchHistoryService.deleteByUid(userId);
        return ResponseUtil.ok();
    }

}