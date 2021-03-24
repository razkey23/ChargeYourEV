package backend.API.views.SessionsPerProvider;

import java.util.List;

public class SessionsPerProvider {
	private List<ProviderSessionSummary> providerSessionSummary;

	public List<ProviderSessionSummary> getProviderSessionSummary() {
		return providerSessionSummary;
	}

	public void setProviderSessionSummary(List<ProviderSessionSummary> providerSessionSummary) {
		this.providerSessionSummary = providerSessionSummary;
	}
	
	
}
