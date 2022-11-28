package com.example.demo.aop.mapper.plan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.BaseReport;
import com.example.demo.entity.ListInventory;
import com.example.demo.entity.actual.ActualDivision;
import com.example.demo.entity.budget.BudgetDivision;
import com.example.demo.entity.plan.PlanDivision;
import com.example.demo.mapper.ListInventoryMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopMeasureMapper {


    @Autowired
    ListInventoryMapper listInventoryMapper;

    //plan
    @Pointcut("execution (* com.example.demo.mapper.plan.PlanDivisionMapper.updateById(..))")
    public void updateByIdPlan() {
    }

    @Pointcut("execution (* com.example.demo.mapper.plan.PlanDivisionMapper.insert(..))")
    public void insertPlan() {
    }

    @Pointcut("execution (* com.example.demo.mapper.plan.PlanDivisionMapper.deleteById(..))")
    public void deletePlan() {
    }

    @Before("updateByIdPlan()")
    public void beforeUpdateByIdPlan(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        extendInventory(listInventoryMapper, (PlanDivision) signatureArgs[0], "jiahua");
    }

    @Before("insertPlan()")
    public void beforeInsertPlan(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        extendInventory(listInventoryMapper, (PlanDivision) signatureArgs[0], "jiahua");
    }

    //actual
    @Pointcut("execution (* com.example.demo.mapper.actual.ActualDivisionMapper.updateById(..))")
    public void updateByIdActual() {
    }

    @Pointcut("execution (* com.example.demo.mapper.actual.ActualDivisionMapper.insert(..))")
    public void insertActual() {
    }

    @Pointcut("execution (* com.example.demo.mapper.actual.ActualDivisionMapper.deleteById(..))")
    public void deleteActual() {
    }

    @Before("updateByIdActual()")
    public void beforeUpdateByIdActual(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        extendInventory(listInventoryMapper, (ActualDivision) signatureArgs[0], "shiji");
    }

    @Before("insertPlan()")
    public void beforeInsertActual(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        extendInventory(listInventoryMapper, (ActualDivision) signatureArgs[0], "shiji");
    }

    //budget
    @Pointcut("execution (* com.example.demo.mapper.budget.BudgetDivisionMapper.updateById(..))")
    public void updateByIdBudget() {
    }

    @Pointcut("execution (* com.example.demo.mapper.budget.BudgetDivisionMapper.insert(..))")
    public void insertBudget() {
    }

    @Pointcut("execution (* com.example.demo.mapper.budget.BudgetDivisionMapper.deleteById(..))")
    public void deleteBudget() {
    }

    @Before("updateByIdBudget()")
    public void beforeUpdateByIdBudget(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        extendInventory(listInventoryMapper, (BudgetDivision) signatureArgs[0], "yusuan");
    }

    @Before("insertPlan()")
    public void beforeInsertBudget(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        extendInventory(listInventoryMapper, (BudgetDivision) signatureArgs[0], "yusuan");
    }

    private void extendInventory(BaseMapper mapper, BaseReport entity, String type) {
        if (entity == null)
            return;
        ListInventory listInventory = new ListInventory();
        listInventory.setTableId((String) entity.fetchPrimeId());
        listInventory.setTableType(type);
        mapper.insert(listInventory);
    }


    /**/

//    @After("test()")
//    public void afterAdvice() {
//        //System.out.println("afterAdvice...");
//    }

    // @Around("test()")
    // public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
//        System.out.println("before");
//        try {
//            proceedingJoinPoint.proceed();
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//        System.out.println("after");
    // }


}
