//package events;
//
//import utility.Pair;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by cyao42 on 4/26/2016.
// */
//public class CarolynEventFactoryTesting {
//
//    private static String groovyScript = "resources/groovyScripts/ACGamePositionHandler.groovy";
//
//    public static Object... propertyTrigMap(Map<String, String> triggerDesc) {
//        triggerDesc.put("trigger_type", "events.PropertyTrigger");
//        triggerDesc.put("entityID", "10");
//        triggerDesc.put("component", "Health");
//        triggerDesc.put("propertyName", "health");
//        return triggerDesc;
//    }
//
//    public static Map<String, String> keyTrigMap(Map<String, String> triggerDesc) {
//        triggerDesc.put("trigger_type", "events.KeyTrigger");
//        triggerDesc.put("key", "b");
//        return triggerDesc;
//    }
//
//    public static Map<String, String> timeTrigMap(Map<String, String> triggerDesc) {
//        triggerDesc.put("trigger_type", "events.TimeTrigger");
//        triggerDesc.put("time", "5031");
//        return triggerDesc;
//    }
//
//    public static void main(String[] args) {
//        EventFactory eventFactory = new EventFactory();
//        Map<String, String> triggerDesc = new HashMap<String, String>();
//        PropertyTrigger pt = (PropertyTrigger) eventFactory.createEvent(propertyTrigMap(triggerDesc), groovyScript)._1();
//        triggerDesc.keySet().clear();
//        TimeTrigger tt = (TimeTrigger) eventFactory.createEvent(timeTrigMap(triggerDesc), groovyScript)._1();
//        triggerDesc.keySet().clear();
//        KeyTrigger kt = (KeyTrigger) eventFactory.createEvent(keyTrigMap(triggerDesc), groovyScript)._1();
//
//    }
//
//}
