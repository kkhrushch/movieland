package com.khrushch.movieland.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khrushch.movieland.model.CurrencyCode;
import com.khrushch.movieland.model.CurrencyRate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CurrencyRateMapper {
    private static final ObjectMapper mapper = new ObjectMapper();
    public List<CurrencyRate> map(String json) throws IOException {
        JsonNode root = mapper.readTree(json);
        Iterator<JsonNode> iterator = root.elements();
        List<CurrencyRate> rates = new ArrayList<>();

        while (iterator.hasNext()) {
            JsonNode currNode = iterator.next();
            String currency = currNode.get("cc").asText();
            double rate = 1D / currNode.get("rate").asDouble();

            if (CurrencyCode.isValidCurrencyCode(currency)) {
                rates.add(new CurrencyRate(CurrencyCode.forName(currency), rate));
            }
        }

        return rates;
    }

}
