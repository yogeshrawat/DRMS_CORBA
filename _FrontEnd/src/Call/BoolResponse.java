package Call;

import java.io.Serializable;

public class BoolResponse implements Serializable {
	private String replicaName;
	private boolean result;
	
	public BoolResponse(String replicaName,boolean result){
		replicaName=this.replicaName;
		result=this.result;
	}

	public String getReplicaName() {
		return replicaName;
	}

	public void setReplicaName(String replicaName) {
		this.replicaName = replicaName;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
}
