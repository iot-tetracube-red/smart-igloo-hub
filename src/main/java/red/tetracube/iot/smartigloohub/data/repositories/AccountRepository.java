package red.tetracube.iot.smartigloohub.data.repositories;

import org.hibernate.reactive.mutiny.Mutiny;
import io.smallrye.mutiny.Uni;
import red.tetracube.iot.smartigloohub.data.entities.Account;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AccountRepository {

    @Inject
    Mutiny.SessionFactory rxSessionFactory;

    public Uni<Account> getAccountFromUsername(String username) {
        return rxSessionFactory.openSession()
        .flatMap(session ->
            session.createQuery("from Account account where account.username = :username",
                    Account.class)
                    .setParameter("username", username)
                    .setMaxResults(1)
                    .getSingleResultOrNull()
                    .eventually(session::close)
        );
    }
}