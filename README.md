# NSetCache

This is a simple implementation of an N-Way Set Associative Cache using Java.

The user can implement their only replacement strategy. Current default replacement strategy is "LRU" (Least Recently Used)

SETUP:
Use ClientCache c = new ClientCache(nWay, numEntries) to instantiate a cache object.
Use ClientCacheSet customSet = new ClientCacheSet(2, “MRU”) to customize the cache replacement policy.
You can pass in an instance of ClientCacheSet as an argument to ClientCache in order to customize replacement policies.
In this exercise, I have designed, implemented and tested a N-way, set-associative cache. When designing this cache, I focused on creating something simple and intuitive but at the same time provide a certain level of customization for clients to build custom solutions on top of the cache I implemented. I will start from a high level and work my way down to the details of this implementation. The main classes that a client would use would be ClientCache and ClientCacheSet. The other files, Cache and CacheSet contain the logic behind the actual cache. 
ClientCache is the most basic cache that follows a least recently used(LRU) replacement policy with a constructor allowing the user to specify a certain number of entries and an integer N, which denotes the number of entries in a set. For further customization, ClientCacheSet is an object that can be modified to incorporate different replacement policies for the cache. The object then can be passed to ClientCache in the constructor as an argument for the ClientCache.
From a high level, the cache is implemented using an ArrayList of CacheSets (subclass of LinkedHashMap) that correspond to a set of the cache. The greatest difficulty of this exercise was creating a suitable hash function that would hash any Java Object for a key and return an index that would correspond to a set. In this implementation, used MD5 hashing to generate a hash value for any object key. The hash value then goes through a simple method that generates an integer using the hash value mod the size of the array. Then, the appropriate index can be retrieved for the object.
I used a LinkedHashMap to provide constant time retrieval and deletion for each set block. In this class, I’ve overwritten the put() method to incorporate different replacement algorithms that the user may want to use. (LRU, MRU, Custom). 
The files also include CacheTestCases which includes some unit tests to test different replacement policies and basic functionality of the cache. Further testing can be implemented in this file.  
