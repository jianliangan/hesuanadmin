package com.example.demo.service.impl.inventory;

import com.example.demo.document.Inventory;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.service.inventory.IInventoryService;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class InventoryServiceImpl implements IInventoryService {
    @Resource
    InventoryRepository inventoryRepository;


    public QueryResponse getList(Map<String, String> qparams) {

        QueryResponse objectMap = inventoryRepository.getList(qparams);

        return objectMap;
    }

    public void save(Inventory inventory) {

        UpdateResponse status = inventoryRepository.save(inventory);
    }

    public void delete(Inventory inventory) {

        UpdateResponse status = inventoryRepository.delete(inventory);
    }
}
