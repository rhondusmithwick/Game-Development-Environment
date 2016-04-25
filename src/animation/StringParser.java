package animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringParser {

	public List<Double> convertStringToDoubleList(String string) {
		List<String> stringList = Arrays.asList(string.split("\\s*,\\s*"));
		List<Double> doubleList = new ArrayList<Double>();
		for(String str: stringList){
			System.out.println(str);
			doubleList.add(Double.parseDouble(str));
			
		}
		return doubleList;
	}

}
