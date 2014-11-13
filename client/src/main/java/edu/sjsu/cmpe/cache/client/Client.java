package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.Hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        //HashingClient caching =new HashingClient(Hashing.md5(Integer.toString(i)),3, cacheNodes);

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
            int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), cacheNodes.size());
            cacheNodes.get(bucket).put(i, mapValue);
            System.out.println("Pushing Map Values into Cache \n");
            System.out.println("Put key: " +i+ " Value: " +mapValue+ "\n");
            System.out.println("###############################################\n");
        }

        for (int i=1;i<=10;i++){
            int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), cacheNodes.size());
            String value=cacheNodes.get(bucket).get(i);
            System.out.println("Retrieving Values from Cache\n");
            System.out.println("Get key: " +i+ " Value: " +value+ "\n");
            System.out.println("###############################################\n");
        }

        System.out.println("Exiting Cache Client...");
    }

}
