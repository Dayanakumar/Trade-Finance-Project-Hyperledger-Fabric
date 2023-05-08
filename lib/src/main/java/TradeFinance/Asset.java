package TradeFinance;

import org.hyperledger.fabric.contract.annotation.DataType;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType()
public class Asset {
	@Property()
    private final String id;

    @Property()
    private final String expiryDate;

    @Property()
    private final String buyer;

    @Property()
    private final String bank;

    @Property()
    private final String seller;
    @Property()
    private final String amount;

    @Property()
    private final String status;

    public Asset(@JsonProperty("id") final String id,@JsonProperty("expiryDate") final String expiryDate,
    		@JsonProperty("buyer") final String buyer,@JsonProperty("bank") final String bank,
    		@JsonProperty("seller") final String seller,@JsonProperty("amount") final String amount,@JsonProperty("status") final String status) {
        this.id = id;
        this.expiryDate = expiryDate;
        this.buyer = buyer;
        this.bank = bank;
        this.seller = seller;
        this.amount = amount;
        this.status = status;
    }
    public String getId() {
        return id;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getBank() {
        return bank;
    }
    public String getSeller() {
        return seller;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
	

}