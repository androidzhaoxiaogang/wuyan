package net.wecash.common.dimension;

public class PersonalityType {
	public static Float getDefaultPersonality(Float f){
		if(f == null){
			f = 0.5f;
		}
		return f;
	}
}
