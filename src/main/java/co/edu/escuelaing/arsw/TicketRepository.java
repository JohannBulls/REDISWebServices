package co.edu.escuelaing.arsw;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TicketRepository {

    private static final Logger logger = Logger.getLogger(TicketRepository.class.getName());

    @Autowired StringRedisTemplate template;

    @Resource(name = "stringRedisTemplate") ListOperations<String, String> listTickets;

    private int ticketnumber = 0;

    /**
     * Constructs a TicketRepository instance.
     */
    public TicketRepository() {
    }

    /**
     * Retrieves the next ticket number and stores it in Redis.
     *
     * @return The next ticket number.
     */
    public synchronized Integer getTicket() {
        try {
            Integer ticket = ticketnumber++;
            listTickets.leftPush("ticketStore", ticket.toString());
            logger.log(Level.INFO, "Generated ticket number: " + ticket);
            return ticket;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error generating ticket", e);
            return null;
        }
    }

    /**
     * Checks if a ticket is valid and removes it from Redis.
     *
     * @param ticket Ticket to check.
     * @return True if the ticket is valid; false otherwise.
     */
    public boolean checkTicket(String ticket) {
        try {
            Long isValid = listTickets.getOperations().boundListOps("ticketStore").remove(0, ticket);
            logger.log(Level.INFO, "Checked ticket: " + ticket + ", isValid: " + isValid);
            return (isValid > 0L);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking ticket", e);
            return false;
        }
    }

    /**
     * Evicts expired tickets from the store.
     * This method can be scheduled to run periodically.
     */
    void eviction() {
        // Implementation for ticket eviction if required
        // For example, delete tickets after timeout or use in checkTicket method
        logger.log(Level.INFO, "Eviction method called");
    }
}
