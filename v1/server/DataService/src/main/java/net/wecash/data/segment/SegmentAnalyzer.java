package net.wecash.data.segment;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * 
 * @author franklin.li
 *
 */
@Component
public class SegmentAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(SegmentAnalyzer.class);
	
	public List<String> getSegedList(Map<String, Integer> map){
		Set<String> set = map.keySet();
		String[] s = new String[set.size()];
		set.toArray(s);
		return Arrays.asList(s);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Integer> segText(String content){
		Map<String, Integer> segFren=new HashMap<String, Integer>();
		try {
			IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(content), true);
			Lexeme lexeme;
			while ((lexeme = ikSegmenter.next()) != null) {
				if(lexeme.getLexemeText().length() > 1){
					if(segFren.containsKey(lexeme.getLexemeText())){
						segFren.put(lexeme.getLexemeText(), segFren.get(lexeme.getLexemeText()) + 1);
					}
					else { 
						segFren.put(lexeme.getLexemeText(), 1); 
					} 

				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		List<Map.Entry<String, Integer>> segFrenList = new ArrayList<Map.Entry<String, Integer>>(segFren.entrySet()); 
		Collections.sort(segFrenList, new Comparator<Map.Entry<String, Integer>>() { 
			public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) { 
				return obj2.getValue() - obj1.getValue(); 
			} 
		}); 
		int topSegsCount = 10;
		Map topSegMap = new HashMap<>();
		for(int i=0; i < topSegsCount && i < segFrenList.size(); i++){ 
			Map.Entry<String,Integer> segFrenEntry = segFrenList.get(i);
			String segStr = segFrenEntry.getKey();
			if(segStr.length() >= 2 && !topSegMap.containsKey(segStr) && segFrenEntry.getValue() >= 2){
				topSegMap.put(segStr, segFrenEntry.getValue()); 
			}
		}
		return topSegMap;
	}
	
	public List<String> reSegTags(List<String> tags){
		List<String> newTags = new ArrayList<String>();
		try{
			Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_40);
			StringReader reader = new StringReader(tags.toString());  
			TokenStream ts = analyzer.tokenStream("tags", reader);
			CharTermAttribute termAttribute = ts.addAttribute(CharTermAttribute.class);
			while(ts.incrementToken()){
				String t = termAttribute.toString();
				newTags.add(t);  
			}  
			analyzer.close();
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return newTags;
	}
}
