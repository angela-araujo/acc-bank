package com.accenture.academico.bank.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    public void testEquals() {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1);
        cliente1.setNome("João");

        Cliente cliente2 = new Cliente();
        cliente2.setId(1);
        cliente2.setNome("João");

        Cliente cliente3 = new Cliente();
        cliente3.setId(2);
        cliente3.setNome("Maria");

        // Teste de igualdade entre dois clientes com o mesmo ID e nome
        assertTrue(cliente1.equals(cliente2));

        // Teste de desigualdade entre dois clientes com IDs diferentes
        assertFalse(cliente1.equals(cliente3));
    }

    @Test
    public void testHashCode() {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1);
        cliente1.setNome("João");

        Cliente cliente2 = new Cliente();
        cliente2.setId(1);
        cliente2.setNome("João");

        // Os objetos com o mesmo ID e nome devem ter o mesmo hashCode
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }
}
