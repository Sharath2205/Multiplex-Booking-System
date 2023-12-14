package com.multiplex.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.multiplex.entity.Hall;
import com.multiplex.entity.Show;

public interface ShowsRepository extends JpaRepository<Show, Integer> {
	@Query("SELECT s FROM Show s WHERE s.movie.movieName = :movieName")
	List<Show> findByMovieName(@Param("movieName") String movieName);

    @Query("SELECT s FROM Show s WHERE s.hall = :hall AND s.slotNo = :slotNo " +
    		 "AND ((s.fromDate BETWEEN :fromDate AND :toDate) OR (s.toDate BETWEEN :fromDate AND :toDate))")
     List<Show> findByHallAndSlotNoAndDates(
             @Param("hall") Hall hall,
             @Param("slotNo") int slotNo,
             @Param("fromDate") LocalDate fromDate,
             @Param("toDate") LocalDate toDate);
    
    @Query("SELECT s FROM Show s " +
            "JOIN FETCH s.hall h " +
            "JOIN FETCH s.movie m " +
            "WHERE m.movieName = :movieName " +
            "AND h.hallDesc = :hallDesc " +
            "AND s.slotNo = :slotNo " +
            "AND s.fromDate <= :bookingDate " +
            "AND s.toDate >= :bookingDate")
     Optional<Show> findShowByMovieNameAndHallDescAndSlotNoAndBookingDate(
             @Param("movieName") String movieName,
             @Param("hallDesc") String hallDesc,
             @Param("slotNo") int slotNo,
             @Param("bookingDate") LocalDate bookingDate
     );
}
