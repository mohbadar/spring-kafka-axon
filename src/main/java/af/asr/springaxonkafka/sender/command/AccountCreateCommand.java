package af.asr.springaxonkafka.sender.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateCommand{
    @TargetAggregateIdentifier
    private String id;
    private String accountHolder;
    private String accountHolderName;
}