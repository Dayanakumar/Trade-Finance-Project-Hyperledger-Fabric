package TradeFinance;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import com.owlike.genson.Genson;


@Contract(
       name = "TradeFinance",
       info = @Info(
               title = "TradeFinance contract",
               description = "A LetterOfCredit chaincode ",
               version = "0.0.1-SNAPSHOT"))


@Default
public final class LetterOfCredit implements ContractInterface {
 
	private final Genson genson = new Genson();
	private enum TradeFinanceErrors {
        Asset_NOT_FOUND,
        Asset_ALREADY_EXISTS
    }
	
	@Transaction()
    public void initLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        // Create and initialize the LetterOfCredit asset
        Asset lc = new Asset("LC001", "12-10-2024", "Dayana", "SBI", "Praveen", "30000", "Active");
        String AssetState = genson.serialize(lc);        
        stub.putStringState("LC001", AssetState);
    }

	@Transaction()
	public String requestLetterOfCredit(final Context ctx, String id, String expiryDate, String buyer, String bank, String seller, String amount, String status) {
	    ChaincodeStub stub = ctx.getStub();	 
	    
	    // Check if the LC with the same ID already exists
	    String lcState = stub.getStringState(id);
	    if (!lcState.isEmpty()) {
	    	String errorMessage = String.format("LC %s does not exist", id);
	    	System.out.println(errorMessage);
	        throw new ChaincodeException(errorMessage, TradeFinanceErrors.Asset_NOT_FOUND.toString());
	    }	    
	    
	    // Create a new LC asset
	    Asset lc = new Asset(id, expiryDate, buyer, bank, seller, amount, status);
	    String assetState = genson.serialize(lc);	
	    
	    // Serialize the updated LC asset     
	    stub.putStringState(id, assetState);  
	    
	    // Return the created LC asset 
	    return assetState;
	}
	
	@Transaction()
	public Asset issueLetterOfCredit(final Context ctx, String id) {
	    ChaincodeStub stub = ctx.getStub();
	    // Check if the LC with the given ID exists
	    
	    String lcState = stub.getStringState(id);
	    if (lcState.isEmpty()) {
	        String errorMessage = String.format("LC %s does not exist", id);
	        System.out.println(errorMessage);
	        throw new ChaincodeException(errorMessage, TradeFinanceErrors.Asset_NOT_FOUND.toString());
	    }	    
	    
	    Asset lc = genson.deserialize(lcState, Asset.class);
	    
        Asset newAsset = new Asset(lc.getId(), lc.getExpiryDate(), lc.getBuyer(), lc.getBank(), lc.getSeller(), lc.getAmount(),"Issued");
	    
        // Serialize the updated LC asset 
	    String newassetState = genson.serialize(lc);
	    stub.putStringState(id, newassetState);
	    
	    return newAsset;
	}
	
	@Transaction()
	public Asset acceptLetterOfCredit(final Context ctx, String id) {
	    ChaincodeStub stub = ctx.getStub();	    
	    
	    String lcState = stub.getStringState(id);
	    if (lcState.isEmpty()) {
	        String errorMessage = String.format("LC %s does not exist", id);
	        System.out.println(errorMessage);
	        throw new ChaincodeException(errorMessage, TradeFinanceErrors.Asset_NOT_FOUND.toString());
	    }	    
	    
	    Asset lc = genson.deserialize(lcState, Asset.class);
	    
        Asset newAsset = new Asset(lc.getId(), lc.getExpiryDate(), lc.getBuyer(), lc.getBank(), lc.getSeller(), lc.getAmount(),"Accepted");
	    
        // Serialize the updated LC asset 
	    String newassetState = genson.serialize(lc);
	    stub.putStringState(id, newassetState);
	    
	    return newAsset;
	}

	@Transaction()
	public Asset viewLetterOfCredit(final Context ctx, final String id) {
	    ChaincodeStub stub = ctx.getStub();
	    
	    String assetState = stub.getStringState(id);
	    if (assetState.isEmpty()) {
	    	String errorMessage = String.format("LC %s does not exist", id);
	    	System.out.println(errorMessage);
	        throw new ChaincodeException(errorMessage, TradeFinanceErrors.Asset_NOT_FOUND.toString());
	    }
	    
	    Asset asset = genson.deserialize(assetState, Asset.class);
	    return asset;
	}
}
