package com.sugo.admin.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sugo.admin.vo.RegionVo;
import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.SugoRegion;
import com.sugo.sql.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/region")
@Validated
public class AdminRegionController {
    private final Log logger = LogFactory.getLog(AdminRegionController.class);

    @Autowired
    private RegionService regionService;

    @GetMapping("/clist")
    public Object clist(@NotNull Integer id) {
        List<SugoRegion> regionList = regionService.queryByPid(id);
        return ResponseUtil.okList(regionList);
    }

    @GetMapping("/list")
    public Object list() {
        List<RegionVo> regionVoList = new ArrayList<>();

        List<SugoRegion> SugoRegions = regionService.getAll();
        Map<Byte, List<SugoRegion>> collect = SugoRegions.stream().collect(Collectors.groupingBy(SugoRegion::getType));
        byte provinceType = 1;
        List<SugoRegion> provinceList = collect.get(provinceType);
        byte cityType = 2;
        List<SugoRegion> city = collect.get(cityType);
        Map<Integer, List<SugoRegion>> cityListMap = city.stream().collect(Collectors.groupingBy(SugoRegion::getPid));
        byte areaType = 3;
        List<SugoRegion> areas = collect.get(areaType);
        Map<Integer, List<SugoRegion>> areaListMap = areas.stream().collect(Collectors.groupingBy(SugoRegion::getPid));

        for (SugoRegion province : provinceList) {
            RegionVo provinceVO = new RegionVo();
            provinceVO.setId(province.getId());
            provinceVO.setName(province.getName());
            provinceVO.setCode(province.getCode());
            provinceVO.setType(province.getType());

            List<SugoRegion> cityList = cityListMap.get(province.getId());
            List<RegionVo> cityVOList = new ArrayList<>();
            for (SugoRegion cityVo : cityList) {
                RegionVo cityVO = new RegionVo();
                cityVO.setId(cityVo.getId());
                cityVO.setName(cityVo.getName());
                cityVO.setCode(cityVo.getCode());
                cityVO.setType(cityVo.getType());

                List<SugoRegion> areaList = areaListMap.get(cityVo.getId());
                List<RegionVo> areaVOList = new ArrayList<>();
                for (SugoRegion area : areaList) {
                    RegionVo areaVO = new RegionVo();
                    areaVO.setId(area.getId());
                    areaVO.setName(area.getName());
                    areaVO.setCode(area.getCode());
                    areaVO.setType(area.getType());
                    areaVOList.add(areaVO);
                }

                cityVO.setChildren(areaVOList);
                cityVOList.add(cityVO);
            }
            provinceVO.setChildren(cityVOList);
            regionVoList.add(provinceVO);
        }

        return ResponseUtil.okList(regionVoList);
    }
}
