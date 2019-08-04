package org.zk.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.zk.backend.data.entity.HistoryItem;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
}
