package ai.profX.service.impl;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import ai.profX.model.CustomSequences;
import ai.profX.service.NextSequenceService;

@Service
public class NextSequenceServiceImpl implements NextSequenceService{
    @Autowired
	private MongoOperations mongo;
    
    @Override
    public long getNextSequence(String seqName)
    {
        CustomSequences counter = mongo.findAndModify(
            query(where("_id").is(seqName)),
            new Update().inc("seq",1),
            options().returnNew(true).upsert(true),
            CustomSequences.class);
        return counter.getSeq();
    }

	@Override
	public int getCurrentSequence(String seqName) {
		 CustomSequences counter = mongo.findAndModify(
		            query(where("_id").is(seqName)),
		            new Update().inc("seq",0),
		            options().returnNew(true).upsert(true),
		            CustomSequences.class);
		        return (int) counter.getSeq();
	}
}
