package roomescape.reservationTime;


import org.springframework.stereotype.Service;
import roomescape.entities.ReservationTime;
import roomescape.repositories.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;


    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public void addTime(ReservationTime reservationTime){
        reservationTimeRepository.save(reservationTime);
    }

    public List<ReservationTime> searchAllTimes(){
        return reservationTimeRepository.findAll();
    }

    public void deleteTime(Long id){
        reservationTimeRepository.deleteById(id);
    }
}