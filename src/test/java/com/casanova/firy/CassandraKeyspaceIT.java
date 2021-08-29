package com.casanova.firy;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.metadata.Metadata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class CassandraKeyspaceIT extends AbstractCassandraTest {

    @Autowired
    private CqlSession session;

    @Test
    void shouldListCassandraUnitKeyspace() {
        Metadata metadata = session.getMetadata();
        assertThat(metadata.getKeyspace(CASSANDRA_UNIT_KEYSPACE)).isNotNull();
    }
}
