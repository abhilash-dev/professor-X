package ai.profX.service;

public interface NextSequenceService {
    public long getNextSequence(String seqName);
    public int getCurrentSequence(String seqName);
}
