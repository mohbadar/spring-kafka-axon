package af.asr.springaxonkafka.query.response;

import af.asr.springaxonkafka.data.Account;

import java.util.List;


public class FindAccountSummariesResponse {

    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}