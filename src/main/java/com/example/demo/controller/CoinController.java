package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Coin;
import com.example.demo.repository.CoinRepository;

/**
 * CoinController.java
 * Description: CRUD
 * @author Neil 2022年5月8日
 * @version 1.0
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CoinController {

	@Autowired
	CoinRepository coinRepository;
	
	/**
	 * Method: Select
	 * @param code
	 * @return
	 */
	@GetMapping("/coins")
	public ResponseEntity<List<Coin>> getAllCoins(@RequestParam(required = false) String code) {
		try {
			List<Coin> coins = new ArrayList<Coin>();

			if (code == null)
				coinRepository.findAll().forEach(coins::add);
			else
				coinRepository.findByCodeContaining(code).forEach(coins::add);

			if (coins.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(coins, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
	 * Method: Select by Number
	 * @param id
	 * @return
	 */
	@GetMapping("/coins/{id}")
	public ResponseEntity<Coin> getCoinById(@PathVariable("id") long id) {
		Optional<Coin> coinData = coinRepository.findById(id);

		if (coinData.isPresent()) {
			return new ResponseEntity<>(coinData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Method: Insert
	 * @param coin
	 * @return
	 */
	@PostMapping("/coins")
	public ResponseEntity<Coin> createCoin(@RequestBody Coin coin) {
		try {
			Coin _coin = coinRepository
					.save(new Coin(coin.getCode(), coin.getSymbol(), coin.getRate(), coin.getDescription(), coin.getRate_float()));
			return new ResponseEntity<>(_coin, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method: Update
	 * @param id
	 * @param coin
	 * @return
	 */
	@PutMapping("/coins/{id}")
	public ResponseEntity<Coin> updateCoin(@PathVariable("id") long id, @RequestBody Coin coin) {
		Optional<Coin> coinData = coinRepository.findById(id);

		if (coinData.isPresent()) {
			Coin _coin = coinData.get();
			_coin.setCode(coin.getCode());
			_coin.setSymbol(coin.getSymbol());
			_coin.setRate(coin.getRate());
			_coin.setDescription(coin.getDescription());
			_coin.setRate_float(coin.getRate_float());
			return new ResponseEntity<>(coinRepository.save(_coin), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Method: Delete
	 * @param id
	 * @return
	 */
	@DeleteMapping("/coins/{id}")
	public ResponseEntity<HttpStatus> deleteCoin(@PathVariable("id") long id) {
		try {
			coinRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method: Delete All
	 * @return
	 */
	@DeleteMapping("/coins")
	public ResponseEntity<HttpStatus> deleteAllCoins() {
		try {
			coinRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 * Method: 資料轉換
	 * @param code
	 * @return
	 */
	@GetMapping("/coinsMes")
	public ResponseEntity<List<Coin>> getAllCoinsMes(@RequestParam(required = false) String code) {
		try {
			// 測試字串
            String str = "{\n"
            		+ "    \"time\": {\n"
            		+ "        \"updated\": \"May 7, 2022 00:11:00 UTC\",\n"
            		+ "        \"updatedISO\": \"2022-05-07T00:11:00+00:00\",\n"
            		+ "        \"updateduk\": \"May 7, 2022 at 01:11 BST\"\n"
            		+ "    },\n"
            		+ "    \"disclaimer\": \"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\n"
            		+ "    \"chartName\": \"Bitcoin\",\n"
            		+ "    \"bpi\": {\n"
            		+ "        \"USD\": {\n"
            		+ "            \"code\": \"USD\",\n"
            		+ "            \"symbol\": \"&#36;\",\n"
            		+ "            \"rate\": \"36,015.8166\",\n"
            		+ "            \"description\": \"United States Dollar\",\n"
            		+ "            \"rate_float\": 36015.8166\n"
            		+ "        },\n"
            		+ "        \"GBP\": {\n"
            		+ "            \"code\": \"GBP\",\n"
            		+ "            \"symbol\": \"&pound;\",\n"
            		+ "            \"rate\": \"29,154.0832\",\n"
            		+ "            \"description\": \"British Pound Sterling\",\n"
            		+ "            \"rate_float\": 29154.0832\n"
            		+ "        },\n"
            		+ "        \"EUR\": {\n"
            		+ "            \"code\": \"EUR\",\n"
            		+ "            \"symbol\": \"&euro;\",\n"
            		+ "            \"rate\": \"34,226.9830\",\n"
            		+ "            \"description\": \"Euro\",\n"
            		+ "            \"rate_float\": 34226.983\n"
            		+ "        }\n"
            		+ "    }\n"
            		+ "}";
			
			List<Coin> coins = new ArrayList<Coin>();
            JSONObject jsonObject = new JSONObject(str);
            final Iterator<String> keysItr = jsonObject.keys();
            
            // 第一層
            while (keysItr.hasNext()) {
            	
            	String key = keysItr.next();
            	Object value = jsonObject.get(key);
            	
            	// 判斷取值找JSON
                if (!(value instanceof String)) {
                	
                    JSONObject bpijsonObject = ((JSONObject) value);
                    
                    final Iterator<String> keysItr2 = bpijsonObject.keys();
                    
                    if("bpi".equals(key)) {
                    	long i = 0;
                    	// 第二層
                         while (keysItr2.hasNext()) {
                        	 
                            String key2 = keysItr2.next();
                            Object value2 = bpijsonObject.get(key2);
                            // 判斷取值找JSON
                            if (!(value2 instanceof String)) {
                            	JSONObject bpijsonObject2 = ((JSONObject) value2);
                            	
                            	// 組Coin
                            	Coin coin = new Coin();
                            	coin.setId(i);
                            	coin.setCode(bpijsonObject2.getString("code"));
                            	coin.setSymbol(bpijsonObject2.getString("symbol"));
                            	coin.setRate(bpijsonObject2.getString("rate"));
                            	coin.setRate_float((bpijsonObject2.getBigDecimal("rate_float")).toString());
                            	if("USD".equals(coin.getCode())) {
                            		coin.setDescription("美元");
                            	}else if("GBP".equals(coin.getCode())){
                            		coin.setDescription("英鎊");
                            	}else if("EUR".equals(coin.getCode())){
                            		coin.setDescription("歐元");
                            	}else {                            		
                            		coin.setDescription(bpijsonObject2.getString("description"));
                            	}
                            	coins.add(coin);
                            	i++;
                            }else {
                            }
                        }
                    }
                }        
            }
			return new ResponseEntity<>(coins, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
