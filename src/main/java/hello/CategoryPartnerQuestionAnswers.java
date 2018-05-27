package hello;

import java.util.List;

public class CategoryPartnerQuestionAnswers {
	private String category;
	private List<PartnerQuestionAnswer> partnerQuestionAnswerList;
	
	public CategoryPartnerQuestionAnswers(String category, List<PartnerQuestionAnswer> partnerQuestionAnswerList) {
		this.category = category;
		this.partnerQuestionAnswerList = partnerQuestionAnswerList;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<PartnerQuestionAnswer> getPartnerQuestionAnswerList() {
		return partnerQuestionAnswerList;
	}
	public void setPartnerQuestionAnswerList(List<PartnerQuestionAnswer> partnerQuestionAnswerList) {
		this.partnerQuestionAnswerList = partnerQuestionAnswerList;
	}
	
	
}
