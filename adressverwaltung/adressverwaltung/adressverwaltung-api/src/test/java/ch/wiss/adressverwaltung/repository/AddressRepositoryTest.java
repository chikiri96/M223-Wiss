package ch.wiss.adressverwaltung.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AddressRepositoryTest {

    @SuppressWarnings("unused")
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void whenSaveAddress_thenReturnAddress() {
        Address address = new Address();
        address.setStreet("Test Street");
        address.setCity("Test City");
        address.setPostalCode("12345");

        Address savedAddress = addressRepository.save(address);

        assertThat(savedAddress.getId()).isNotNull();
        assertThat(savedAddress.getStreet()).isEqualTo(address.getStreet());
        assertThat(savedAddress.getCity()).isEqualTo(address.getCity());
        assertThat(savedAddress.getPostalCode()).isEqualTo(address.getPostalCode());
    }

}
