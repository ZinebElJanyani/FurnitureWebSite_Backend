package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.Entities.Region;
import ma.org.comfybackend.security.Repositories.RegionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommandServiceImpl implements CommandService{

    RegionRepository regionRepository;

    public CommandServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public List<Region> listRegions() {
        return regionRepository.findAll();
    }
}
