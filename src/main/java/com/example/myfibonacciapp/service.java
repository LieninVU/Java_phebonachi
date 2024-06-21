package com.example.myfibonacciapp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class service
{

    @Autowired
    private Repository repository;

    private Map<Integer, BigInteger> memoizationMap = new HashMap<>();

    @Autowired
    private Calc calculator;

    public BigInteger getFibonacciNumber(int index)
    {
        if (index < 1)
        {
            throw new IllegalArgumentException("Index should be greater or equal to 1");
        }

        if (memoizationMap.containsKey(index))
        {
            return memoizationMap.get(index);
        }

        //поиск в базе данных
        Optional<entity> entityOptional = repository.findByIndex(index);
        if (entityOptional.isPresent())
        {
            BigInteger value = entityOptional.get().getValue();
            memoizationMap.put(index, value); //кэш в памяти
            return value;
        } else
        {
            //расчёт значения и сохранение в базу
            BigInteger value = calculateFibonacci(index);
            entity entit = new entity();
            entit.setIndex(index);
            entit.setValue(value);
            repository.save(entit);
            return value;
        }
    }

    private BigInteger calculateFibonacci(int index)
    {
        if (index == 1 || index == 2)
        {
            return BigInteger.ONE;
        }

        //итерационно с BigInteger
        BigInteger a = BigInteger.ONE, b = BigInteger.ONE, c = BigInteger.ZERO;
        for (int i = 3; i <= index; i++)
        {
            c = a.add(b);
            a = b;
            b = c;
        }
        return c;
    }
}

