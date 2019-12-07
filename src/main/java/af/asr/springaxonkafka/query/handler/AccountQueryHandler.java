package af.asr.springaxonkafka.query.handler;

import af.asr.springaxonkafka.data.AccountRepository;
import af.asr.springaxonkafka.query.CountAccountSummariesQuery;
import af.asr.springaxonkafka.query.FindAccountSummariesQuery;
import af.asr.springaxonkafka.query.response.CountAccountSummariesResponse;
import af.asr.springaxonkafka.query.response.FindAccountSummariesResponse;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AccountQueryHandler {

    @Autowired
    private AccountRepository accRepo;

    @QueryHandler
    public FindAccountSummariesResponse handle(FindAccountSummariesQuery query) {
        FindAccountSummariesResponse response = new FindAccountSummariesResponse();
        response.setAccounts(accRepo.findAll());
        return response;
    }

    @QueryHandler
    public CountAccountSummariesResponse handle(CountAccountSummariesQuery query) {
        CountAccountSummariesResponse response = new CountAccountSummariesResponse();
        response.setCount(accRepo.findAll().size());
        return response;
    }


}