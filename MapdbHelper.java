import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.util.*;

/**
 * Created by sunyf on 2018/3/27.
 */
public class MapdbHelper {
    private static String path;
    private static MapdbHelper mapdbHelper;
    private MapdbHelper(){}
    private static void init(String path){
        MapdbHelper.path = path;
    }

    /**
     * 存储set
     * @param setName
     * @param strings
     */
    private static void createSet(String setName, Set<String> strings) {

        // creo il db
        DB db = DBMaker.fileDB(path).make();

        // Creo un insieme nel db
        HTreeMap.KeySet<String> hashSet = db.hashSet(setName).serializer(Serializer.STRING).createOrOpen();
        if(strings != null){
        for (String s:strings) {
            hashSet.add(s);
        }}
        db.close();
    }

    /**
     * 读取set集合
     * @param setName
     * @return
     */
    private static Set<String> readSet(String setName) {
        DB db = DBMaker.fileDB(path).make();
        HashSet<String> objects = new HashSet<>();
        HTreeMap.KeySet<String> hashSet = db.hashSet(setName).serializer(Serializer.STRING).createOrOpen();
        for (String s: hashSet){
            objects.add(s);
        }
        db.close();
        return objects;
    }

    /**
     * 判断是否存在
     * @param setName
     * @param value
     * @return
     */
    private static boolean exitSetValue(String setName,String value) {
        DB db = DBMaker.fileDB(path).make();
        HTreeMap.KeySet<String> hashSet = db.hashSet(setName).serializer(Serializer.STRING).createOrOpen();
        boolean contains = hashSet.contains(value);
        db.close();
        return contains;
    }

    public static void main(String[] args) {
        init("D:\\workspace\\git\\mp-template\\sender\\mapdb\\map.db");
        Set<String> objects = new HashSet<>();
        objects.add("123");
        MapdbHelper.createSet("wowo",objects);
        Set<String> hahah = MapdbHelper.readSet("wowo");
        for (String s: hahah){
            System.out.println(s);
        }
        boolean wowo = MapdbHelper.exitSetValue("wowo", "123");
        System.out.println(wowo);
    }
}
