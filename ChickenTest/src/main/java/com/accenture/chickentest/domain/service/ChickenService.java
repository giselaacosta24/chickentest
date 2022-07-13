package com.accenture.chickentest.domain.service;

import com.accenture.chickentest.domain.model.Chicken;
import com.accenture.chickentest.domain.repository.ChickenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChickenService implements ChickenServ{

    private final ChickenRepository chickenRepository;

    public ChickenService(ChickenRepository chickenRepository) {
        super();
        this.chickenRepository = chickenRepository;
    }


    @Override
    public List<Chicken> getAllChickens() {
        return chickenRepository.findAll();
    }

    @Override
    public Chicken createChicken(Chicken chicken) {
        return chickenRepository.save(chicken);
    }

/*    @Override
    public Chicken updateChicken(long id, Chicken chickenRequest) {
        Chicken chicken = chickenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chicken", "id", id));
        chicken.setPrice(chickenRequest.getPrice());

        chicken.setAmountDays(chickenRequest.getAmountDays());
        chicken.setIdFarmer(chickenRequest.getIdFarmer());
        return chickenRepository.save(chicken);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.delete(post);
    }

    @Override
    public Post getPostById(long id) {
        Optional<Post> result = postRepository.findById(id);
        if(result.isPresent()) {
            return result.get();
        }else {
            throw new ResourceNotFoundException("Post", "id", id);
        }


    }*/


}
