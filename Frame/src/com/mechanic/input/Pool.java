package com.mechanic.input;

import java.util.ArrayList;
import java.util.List;

public class Pool<T>
{
	public interface PoolFactory<T>
	{
		public T Create();
	}
	
	private final List<T> Objects;
	private final PoolFactory<T> Factory;
	private final int MaxSize;
	
	public Pool(PoolFactory<T> Factory, int MaxSize)
	{
		this.Factory = Factory;
		this.MaxSize = MaxSize;
		Objects = new ArrayList<T>(MaxSize);
	}
	
	public T NewObject()
	{
		T obj = null;
		if (Objects.size() == 0)
			obj = Factory.Create();
		else
			obj = Objects.remove(Objects.size() - 1);
		
		return obj;
	}
	
	public void Free(T object)
	{
		if (Objects.size() < MaxSize)
			Objects.add(object);
	}
}
