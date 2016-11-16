package hello;

import java.util.List;

public class UpdateQuizResponseDTO {
	private String name;
	private List<UpdateQuestionAnswerDTO> updateQuestionAnswerDTOList;

//	private List<UpdateQuestionAnswerDTO> updateQuestionAnswerDTOList = new ArrayList<UpdateQuestionAnswerDTO>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UpdateQuizResponseDTO() {
		// blank constructor
	}

	public List<UpdateQuestionAnswerDTO> getUpdateQuestionAnswerDTOList() {
		return updateQuestionAnswerDTOList;
	}

	public void setUpdateQuestionAnswerDTOList(List<UpdateQuestionAnswerDTO> updateQuestionAnswerDTOList) {
		this.updateQuestionAnswerDTOList = updateQuestionAnswerDTOList;
	}

//	public List<UpdateQuestionAnswerDTO> getUpdateQuestionAnswerDTOList() {
//		return updateQuestionAnswerDTOList;
//	}
//
//	public void setUpdateQuestionAnswerDTOList(List<UpdateQuestionAnswerDTO> updateQuestionAnswerDTOList) {
//		this.updateQuestionAnswerDTOList = updateQuestionAnswerDTOList;
//	}

	
}
