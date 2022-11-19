package com.example.demo.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Base;
import com.example.demo.entity.dict.Dict;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.service.IMyService;
import com.example.demo.service.common.PageData;
import com.example.demo.service.dict.IDictService;
import com.example.demo.service.process.ITreeService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public abstract class BaseController<T extends Base> {
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    private IDictService dictService;
    @Value("${version}")
    private String version;
    protected int pageSize = 20;
    private static final SnowFlake snowFlake = new SnowFlake(1, 1);

    protected JSONObject getDictInfosDivison() {
        JSONObject tmp = new JSONObject();

        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("type_name", "subject");
        wrapper.allEq(wheres);
        List<Dict> dictList = dictService.list(wrapper);
        tmp.put("subject", dictList);

        QueryWrapper wrapper2 = new QueryWrapper();
        Map wheres2 = new HashMap<String, String>();
        wheres2.put("type_name", "category");
        wrapper2.allEq(wheres2);
        List<Dict> dictList2 = dictService.list(wrapper2);
        tmp.put("category", dictList2);
        return tmp;

    }

    protected JSONObject getDictInfosSuppliertype() {
        JSONObject tmp = new JSONObject();

        QueryWrapper wrapper = new QueryWrapper();
        Map wheres = new HashMap<String, String>();
        wheres.put("type_name", "suppliertype");
        wrapper.allEq(wheres);
        List<Dict> dictList = dictService.list(wrapper);
        tmp.put("suppliertype", dictList);
        return tmp;

    }

    protected JSONObject getDictInfosMachine() {
        JSONObject tmp = new JSONObject();

        QueryWrapper wrapper2 = new QueryWrapper();
        Map wheres2 = new HashMap<String, String>();
        wheres2.put("type_name", "category");
        wrapper2.allEq(wheres2);
        List<Dict> dictList2 = dictService.list(wrapper2);
        tmp.put("category", dictList2);
        return tmp;

    }

    //测试
    public static SnowFlake getSnowFlake() {
        return snowFlake;
    }

    @Data
    public static class ResData {
        String code;
        String err;
        String message;
        Object data;
    }

    @GetMapping("/version")
    public String getVersion(HttpServletRequest request) {
        return version;
    }

    @NeedLogin
    @GetMapping("/fetch")
    public ResData getGeneral(HttpServletRequest request) {

        String err = null;
        PageData pageData = new PageData();
        if (fetchService() == null) {
            err = "服务器错误";
        }
        if (err == null) {
            err = commonPreFetchCheck(request);

            if (err == null) {

                int pageIndex =
                        Integer.parseInt(
                                request.getParameter("page") == null ? "0" : request.getParameter("page"));

                pageIndex = (pageIndex == 0 ? 1 : pageIndex);
                // List<T> list = fetchService().list();

                Page<T> page = new Page(pageIndex, pageSize);
                QueryWrapper wrapper = fetchWrapper(request);
                long limit0 = (pageIndex - 1) * pageSize;
                long limit1 = pageIndex * pageSize;
                wrapper.last(" limit " + limit0 + "," + limit1);
                IPage userPage = fetchService().page(page, wrapper);
                wrapper.last("");
                long total = fetchService().count(wrapper);
                userPage.setTotal(total);
                // userPage.getRecords().forEach(System.out::println);
                List<T> list = userPage.getRecords();

                pageData.setItemTotal(userPage.getTotal());
                pageData.setPageSize(userPage.getSize());
                pageData.setList(list);
            }
        }
        ResData resData = new ResData();
        resData.setCode("200");
        if (err != null) {
            resData.setErr(err);
        }
        resData.setData(pageData);
        resData.setMessage("");
        return resData;
    }

    @NeedLogin
    @PostMapping("/push")
    public ResData addGeneral(@RequestBody @Valid List<T> rows, HttpServletRequest request) {

        String err = null;
        err = commonPrePushCheck(request);
        String primeId = "";
        if (err == null) {
            if (fetchService() == null) {
                err = "服务器错误";
            }
            if (rows.size() > 2) {
                err = "请求错误：条数太多";
            }
            if (err == null) {
                for (Integer i = 0; i < rows.size(); i++) {
                    if (rows.get(i).getCmd().equals("edit")) {
                        filterUpdateRow("pre", "edit", rows.get(i), request);
                        Boolean ok = fetchService().updateById(rows.get(i));
                        filterUpdateRow("last", "edit", rows.get(i), request);
                        if (ok == false) {
                            err = "不能修改这条数据";
                            break;
                        }
                    } else if (rows.get(i).getCmd().equals("add")) {
                        filterUpdateRow("pre", "add", rows.get(i), request);
                        primeId = getSnowFlake().nextId() + "";
                        rows.get(i).pushPrimeId(primeId);

                        fetchService().save(rows.get(i));
                        filterUpdateRow("last", "add", rows.get(i), request);
                    } else if (rows.get(i).getCmd().equals("delete")) {
                        err = fetchService().checkCanDelete(rows.get(i));
                        if (err != null) {
                            break;
                        }
                        filterUpdateRow("pre", "delete", rows.get(i), request);
                        Boolean ok = fetchService().removeById(rows.get(i));
                        filterUpdateRow("last", "delete", rows.get(i), request);
                        if (ok == false) {
                            err = "不能删除这条数据";
                            break;
                        }
                    }
                }
            }
        }
        ResData resData = new ResData();
        resData.setCode("200");
        if (err != null) {
            resData.setErr(err);
        }
        resData.setMessage("");
        resData.setData(primeId);
        return resData;
    }

    /**
     * 先根据where确定数据范围，然后将这片数据变成树 参数只接收rootId,<where条件> 其中rootId表示范围内从哪里算根 <where条件>用来确定范围</where条件>
     *
     * @param request
     * @return
     */
    @NeedLogin
    @GetMapping("/tree")
    public ResData getTree(HttpServletRequest request) {
        if (fetchService() == null) {
            return null;
        }
        String cmd = request.getParameter("cmd") == null ? "" : request.getParameter("cmd");
        String rootId = request.getParameter("rootId"); // 只用树做对比不能用在where后面
        String ownId = request.getParameter("ownId") == null ? "" : request.getParameter("ownId");
        List<T> relist = null;
        int pageIndex = 1;
        String err = null;

        err = commonPreFetchCheck(request);
        PageData pageData = null;
        if (err == null) {

            //Page<T> page = new Page(pageIndex, 500);
            QueryWrapper wrapper = fetchWrapper(request);

            //userPage = fetchService().page(page, wrapper);

            // userPage.getRecords().forEach(System.out::println);
            List<T> list = fetchService().list(wrapper);//userPage.getRecords();


            System.out.println(",,,,,6678,,,,," + list);


            int itemOffset = (pageIndex - 1) * pageSize;

            // 树形

            relist = new ArrayList<T>();
            for (int i = 0; i < list.size(); i++) {
                T value = list.get(i);
                if (value.fetchParentId().toString().equals(rootId)) {
                    list.remove(i);
                    relist.add((T) ITreeService.treeLoop0(value, (List<Base>) list));
                    i = -1;
                }
            }


            pageData = new PageData();
            pageData.setItemTotal(1);
            pageData.setPageSize(1);
            if (cmd.equals("self") && fetchService().myKeyName() != null) {

                wrapper.clear();
                Map wheres = new HashMap<String, String>();
                wheres.put(fetchService().myKeyName(), ownId);
                wrapper.allEq(wheres);
                T tmp = (T) fetchService().getOne(wrapper);
                tmp.setChildren(relist);

                List relist2 = new ArrayList<T>();
                relist2.add(tmp);
                pageData.setList(relist2);
            } else {
                pageData.setList(relist);
            }
        }
        ResData resData = new ResData();
        resData.setCode("200");
        if (err != null) {
            resData.setErr(err);
        }
        resData.setData(pageData);
        resData.setMessage("");
        return resData;
    }

    public void filterUpdateRow(String pre, String cmd, T row, HttpServletRequest request) {
        ;
    }


    protected abstract IMyService fetchService();

    protected QueryWrapper fetchWrapper(HttpServletRequest request) {
        String ownId = request.getParameter("ownId") == null ? "" : request.getParameter("ownId");

        QueryWrapper wrapper = new QueryWrapper();

        List orderColumn = new ArrayList<>();
        orderColumn.add("sort");
        wrapper.orderBy(true, true, orderColumn);
        Map wheres = new HashMap<String, String>();
        wheres.put("own_id", ownId);
        if (ownId != "") wrapper.allEq(wheres);
        return wrapper;
    }

    protected abstract String commonPrePushCheck(HttpServletRequest request);

    protected abstract String commonPreFetchCheck(HttpServletRequest request);


}
