package af.asr.springaxonkafka.resource;


import java.time.Instant;
import java.util.List;

import af.asr.springaxonkafka.data.Account;
import af.asr.springaxonkafka.data.AccountRepository;
import af.asr.springaxonkafka.sender.event.AccountCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ProcessingGroup("Accounts")
@RequestMapping("/accounts")
public class AccountQueryController {

    @Autowired
    private AccountRepository accRepo;

    @EventHandler
    public void on(AccountCreatedEvent event, @Timestamp Instant instant) {
        Account account = new Account(event.getId(),event.getBalance(),event.getAccHolder(),event.getAccHolderName(),instant.toString());

        accRepo.insert(account);

    }
}
