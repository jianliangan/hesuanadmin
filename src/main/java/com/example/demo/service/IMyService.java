package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface IMyService<T extends Base> extends IService<T> {

    default public String checkCanDelete(T instan) {
        return null;
    }

    default public Object myKeyName() {
        return null;
    }

    default public T getPrimeOne(Object key) {
        return null;
    }


    static public boolean updateMachineWith(BaseMachine entity, BaseMapper mapper, BaseMapper mapperMachine, String cmd) {
        BaseReport budgetDivision = (BaseReport) mapper.selectById(entity.getOwnId());
        if (budgetDivision == null) {
            throw new ValidationException("当前不在清单条目上");
        }
        IMyService.commonComputPrice(mapperMachine, budgetDivision, true, cmd);
        mapper.updateById(budgetDivision);
        return true;
    }

    static <T extends BaseReport, T2 extends BaseMachine> void commonComputPrice(BaseMapper<T2> mapper, T row, Boolean multiple, String cmd) {

        if (cmd.equals("plan") || cmd.equals("actual")) {
            commonComputPrice2(mapper, (PlanAll) row, multiple, cmd);
            return;
        }

        BigDecimal amount = row.getWorkAmount();
        BigDecimal unitPrice = row.getCostUnitprice();
        BigDecimal sumold = amount.multiply(unitPrice);

        if (multiple != true) {
            row.setCostSumprice(sumold);
            return;
        }
        QueryWrapper wrapperMachine = new QueryWrapper();
        wrapperMachine.select("sum(combined_price) as combinedSum ");
        Map wheresMachine = new HashMap<String, String>();
        wheresMachine.put("own_id", row.fetchPrimeId());
        wrapperMachine.allEq(wheresMachine);
        T2 machine = (T2) mapper.selectOne(wrapperMachine);
        BigDecimal machineSum;
        if (machine == null)
            machineSum = new BigDecimal(0);
        else
            machineSum = machine.getCombinedSum();
        row.setCostSumprice(sumold.add(machineSum));
    }

    static <T2 extends BaseMachine> void commonComputPrice2(BaseMapper<T2> mapper, PlanAll row, Boolean multiple, String cmd) {
        BigDecimal amount = row.getWorkAmount();
        BigDecimal unitPrice = row.getCostUnitprice();
        BigDecimal sumold = amount.multiply(unitPrice);

        if (multiple != true) {
            row.setCostSumprice(sumold);
            return;
        }
        QueryWrapper wrapperMachine = new QueryWrapper();
        Map wheresMachine = new HashMap<String, String>();
        wheresMachine.put("own_id", row.fetchPrimeId());
        wrapperMachine.allEq(wheresMachine);

        List<BaseMachine> machine = mapper.selectList(wrapperMachine);

        BigDecimal machineSum = new BigDecimal(0);

        BigDecimal costManpriceSum = new BigDecimal(0);
        BigDecimal costMaterialspriceSum = new BigDecimal(0);
        BigDecimal costMechanicspriceSum = new BigDecimal(0);
        BigDecimal costDevicepriceSum = new BigDecimal(0);
        BigDecimal costSubpackagepriceSum = new BigDecimal(0);

        for (int i = 0; i < machine.size(); i++) {
            machineSum = machineSum.add(machine.get(i).getCombinedPrice());
            if (machine.get(i).getCategory().equals("人")) {
                costManpriceSum = costManpriceSum.add(machine.get(i).getCombinedPrice());
            } else if (machine.get(i).getCategory().equals("材")) {
                costMaterialspriceSum = costMaterialspriceSum.add(machine.get(i).getCombinedPrice());
            } else if (machine.get(i).getCategory().equals("机")) {
                costMechanicspriceSum = costMechanicspriceSum.add(machine.get(i).getCombinedPrice());
            } else if (machine.get(i).getCategory().equals("设备")) {
                costDevicepriceSum = costDevicepriceSum.add(machine.get(i).getCombinedPrice());
            } else if (machine.get(i).getCategory().equals("专业")) {
                costSubpackagepriceSum = costSubpackagepriceSum.add(machine.get(i).getCombinedPrice());
            }
        }
        row.setCostManprice(costManpriceSum);
        row.setCostMaterialsprice(costMaterialspriceSum);
        row.setCostMechanicsprice(costMechanicspriceSum);
        row.setCostDeviceprice(costDevicepriceSum);
        row.setCostSubpackageprice(costSubpackagepriceSum);


        row.setCostSumprice(sumold.add(machineSum));
    }

    default public IMyService getMachineService() {
        return null;
    }

    default public <T extends BaseReport, T2 extends BaseMachine> String save2update(T entity, BaseMapper<T> ss, BaseMapper<T2> ssMachine, String cmd) {

        QueryWrapper wrapper = new QueryWrapper<T>();
        Map<String, String> stringMap = new HashMap<>();
        String err = "";
        if (entity.getCode() != null && entity.getCode().length() > 50) {
            err = "编码长度不能超过50,";
        }
        if (entity.getCategory() != null && entity.getCategory().length() > 50) {
            err = "编码长度不能超过50,";
        }
        if (entity.getName() != null && entity.getName().length() > 50) {
            err = "编码长度不能超过50,";
        }
        if (entity.getDistinction() != null && entity.getDistinction().length() > 800) {
            err = "编码长度不能超过800,";
        }
        if (entity.getUnit() != null && entity.getUnit().length() > 50) {
            err = "单位长度不能超过50,";
        }
        if (!err.equals("")) {
            throw new ValidationException(err);
        }
        stringMap.put("own_id", entity.getOwnId() == null ? "" : entity.getOwnId());
        stringMap.put("name", entity.getName() == null ? "" : entity.getName());
        stringMap.put("distinction", entity.getDistinction() == null ? "" : entity.getDistinction());
        wrapper.allEq(stringMap);

        BaseReport actualDivision = ss.selectOne(wrapper);
        if (actualDivision != null) {
            entity.pushPrimeId(actualDivision.fetchPrimeId());
            entity.setOwnId(actualDivision.getOwnId());
            entity.setParentId(actualDivision.getParentId());
            entity.setTag(actualDivision.getTag());
            entity.setSort(actualDivision.getSort());
            IMyService.commonComputPrice(ssMachine, entity, true, cmd);
            ss.updateById(entity);
        } else {
            IMyService.commonComputPrice(ssMachine, entity, false, cmd);

            ss.insert(entity);
        }

        return (String) entity.fetchPrimeId();
    }

    default public <T extends BaseOther> String save3update(@Valid T entity, IMyService<T> ss) {

        QueryWrapper wrapper = new QueryWrapper<T>();
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("name", entity.getName() == null ? "" : entity.getName());
        wrapper.allEq(stringMap);

        BaseOther actualDivision = ss.getOne(wrapper);
        if (actualDivision != null) {
            entity.pushPrimeId(actualDivision.fetchPrimeId());
            entity.setOwnId(actualDivision.getOwnId());
            entity.setParentId(actualDivision.getParentId());
            entity.setTag(actualDivision.getTag());
            entity.setSort(actualDivision.getSort());
            ss.updateById(entity);
        } else {
            ss.save(entity);
        }
        return (String) entity.fetchPrimeId();
    }
}
