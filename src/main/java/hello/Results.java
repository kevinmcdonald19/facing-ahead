package hello;

public class Results {

	private PartnerQuizResponse partnerQuizResponse;
	private User partnerOne;
	private User partnerTwo;

	public Results(User partnerOne, User partnerTwo) {
		partnerQuizResponse = new PartnerQuizResponse();
		this.partnerOne = partnerOne;
		this.partnerTwo = partnerTwo;
	}

	public PartnerQuizResponse getPartnerQuizResponse() {
		return partnerQuizResponse;
	}

	public void setPartnerQuizResponse(PartnerQuizResponse partnerQuizResponse) {
		this.partnerQuizResponse = partnerQuizResponse;
	}

	public User getPartnerOne() {
		return partnerOne;
	}

	public void setPartnerOne(User partnerOne) {
		this.partnerOne = partnerOne;
	}

	public User getPartnerTwo() {
		return partnerTwo;
	}

	public void setPartnerTwo(User partnerTwo) {
		this.partnerTwo = partnerTwo;
	}

}
