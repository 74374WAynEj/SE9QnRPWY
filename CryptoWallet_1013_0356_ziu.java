// 代码生成时间: 2025-10-13 03:56:22
import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Represents a Crypto Wallet entity
 */
@Entity
public class CryptoWallet extends Model {

    @Id
    private Long id;
    private String address;
    private double balance;

    /**
     * Default constructor
     */
    public CryptoWallet() {
    }

    /**
     * Constructor with parameters
     * @param address Wallet address
     * @param balance Initial balance
     */
    public CryptoWallet(String address, double balance) {
        this.address = address;
        this.balance = balance;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Adds funds to the wallet
     * @param amount Amount to add
     */
    public void addFunds(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.balance += amount;
    }

    /**
     * Withdraws funds from the wallet
     * @param amount Amount to withdraw
     * @return The remaining balance
     */
    public double withdrawFunds(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (this.balance < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        this.balance -= amount;
        return this.balance;
    }

    /**
     * Lists all wallets in the database
     * @return A list of all wallets
     */
    public static List<CryptoWallet> findAllWallets() {
        return find.all();
    }
}
