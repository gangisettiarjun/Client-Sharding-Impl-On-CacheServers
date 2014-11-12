package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache1 = new DistributedCacheService(
                "http://localhost:3000");
        CacheServiceInterface cache2 = new DistributedCacheService(
                "http://localhost:3001");
        CacheServiceInterface cache3 = new DistributedCacheService(
                "http://localhost:3002");

        ArrayList<CacheServiceInterface> cacheNodes= new ArrayList();
        cacheNodes.add(cache1);
        cacheNodes.add(cache2);
        cacheNodes.add(cache3);

        HashingClient caching =new HashingClient(Hashing.md5(), 10, cacheNodes);

        Map<Integer, String> mapStore = new HashMap<Integer, String>();
        mapStore.put(1, "a");
        mapStore.put(2, "b");
        mapStore.put(3, "c");
        mapStore.put(4, "d");
        mapStore.put(5, "e");
        mapStore.put(6, "f");
        mapStore.put(7, "g");
        mapStore.put(8, "h");
        mapStore.put(9, "i");
        mapStore.put(10, "j");

        for (int i=1;i<=10;i++){
            String mapValue = mapStore.get(i);
            CacheServiceInterface bucket = (CacheServiceInterface) caching.get(i);
            bucket.put(i, mapValue);
            System.out.println("Put key" +i+ "Value" +mapValue+ "in cache server" +bucket.toString());
        }

        for (int i=1;i<=10;i++){
            CacheServiceInterface bucket = (CacheServiceInterface) caching.get(i);
            String value= bucket.get(i);
            System.out.println("Get key" +i+ "Value" +value+ "in cache server" +bucket.toString());
        }

        System.out.println("Exiting Cache Client...");
    }

}
