package co.edu.escuelaing.arsw;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for {@link DrawingServiceController}.
 */
class DrawingServiceControllerTest {

    @Mock
    private TicketRepository ticketRepositoryMock;

    @InjectMocks
    private DrawingServiceController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test case for status endpoint.
     */
    @Test
    void testStatusEndpoint() {
        // Mock behavior of ticket repository
        when(ticketRepositoryMock.getTicket()).thenReturn(123);

        // Call controller method
        String statusResponse = controller.status();

        // Verify expected response
        assertTrue(statusResponse.contains("{\"status\":\"Greetings from Spring Boot."));
        assertFalse(statusResponse.contains(". The server is Running!\"}"));
    }

    /**
     * Test case for getTicket endpoint.
     */
    @Test
    void testGetTicketEndpoint() {
        // Mock behavior of ticket repository
        when(ticketRepositoryMock.getTicket()).thenReturn(456);

        // Call controller method
        String ticketResponse = controller.getTicket();

        // Verify expected response
        assertTrue(ticketResponse.contains("{\"ticket\":\"456\"}"));
    }

    /**
     * Test case for error handling in status endpoint.
     */
    @Test
    void testStatusEndpointErrorHandling() {
        // Simulate error in ticket repository
        when(ticketRepositoryMock.getTicket()).thenReturn(null);

        // Call controller method
        String statusResponse = controller.status();

        // Verify response does not indicate error or exception
        assertFalse(statusResponse.contains("Error") || statusResponse.contains("exception"));
    }
}
