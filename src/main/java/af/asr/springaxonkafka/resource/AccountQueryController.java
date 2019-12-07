package af.asr.springaxonkafka.resource;


import java.time.Instant;
import java.util.List;
import java.util.UUID;

import af.asr.springaxonkafka.data.Account;
import af.asr.springaxonkafka.data.AccountRepository;
import af.asr.springaxonkafka.sender.command.AccountCreateCommand;
import af.asr.springaxonkafka.sender.event.AccountCreatedEvent;
import af.asr.springaxonkafka.sender.event.MoneyDepositedEvent;
import af.asr.springaxonkafka.sender.event.MoneyWithdrawnEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;


@RestController
//@ProcessingGroup("Accounts")
@RequestMapping("/accounts")
public class AccountQueryController {

    @Autowired
    private AccountRepository accRepo;

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init()
    {


        commandGateway.sendAndWait(new AccountCreateCommand(UUID.randomUUID().toString(),"123123", "Ahmad"));
    }

    @EventHandler
    public void on(AccountCreatedEvent event, @Timestamp Instant instant) {
        System.out.print("Account Data :: "+ event.toString());
        Account account = new Account(event.getId(),event.getBalance(),event.getAccHolder(),event.getAccHolderName(),instant.toString());
        accRepo.insert(account);

    }

    @EventHandler
    public void on(MoneyDepositedEvent event, @Timestamp Instant instant) {
        Account account = accRepo.findByAccountNo(event.getId());
        account.setBalance(account.getBalance().add(event.getAmount()));
        account.setLastUpdated(instant.toString());
        accRepo.save(account);
    }


    @EventHandler
    public void on(MoneyWithdrawnEvent event, @Timestamp Instant instant) {
        Account account = accRepo.findByAccountNo(event.getId());
        account.setBalance(account.getBalance().subtract(event.getAmount()));
        account.setLastUpdated(instant.toString());
        accRepo.save(account);
    }

    @GetMapping("/details")
    public List<Account> getAccDetails() {
        return accRepo.findAll();
    }

    @GetMapping("/details/{id}")
    public Account getAccDetails(@PathVariable String id) {
        return accRepo.findByAccountNo(id);
    }


    @PostMapping(value = "/create")
    public Account createNewAccount(@RequestBody(required = true) String data) throws JsonProcessingException {

        JsonNode root =objectMapper.readTree(data);
        System.out.print("Account Data :: "+ root.asText());


        return null;
    }

}
