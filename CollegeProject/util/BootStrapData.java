package com.greatlearning.orders.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.greatlearning.orders.dao.OrderJpaRepo;

import com.greatlearning.orders.dao.UserJpaRepo;
import com.greatlearning.orders.model.LineItem;
import com.greatlearning.orders.model.Order;
import com.greatlearning.orders.model.Roles;
import com.greatlearning.orders.model.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BootStrapData {
	
	
	// private int OrderCount;
	private final UserJpaRepo repo;

	private final OrderJpaRepo jparepo;

	private final Faker faker = new Faker();

//application listen will listen to any application event and after the application get built it will fire the below method
	@EventListener(ApplicationReadyEvent.class)
	public void HandleApplicationReadyEvent(ApplicationReadyEvent event) {

		IntStream.range(0, 100).forEach(index -> {
			LocalDate orderdate = faker.date().past(2, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			Name customerName = faker.name();
			Order order = Order.builder().customerName(customerName.firstName()).orderdate(orderdate)
					.email(customerName + "@" + faker.internet().domainName()).price((double) index).build();

			IntStream.range(2, faker.number().numberBetween(2, 5)).forEach(val -> {
				LineItem lineitem = LineItem.builder().name(faker.commerce().productName())
						.price(faker.number().randomDouble(2, 800, 1200)).qty(faker.number().numberBetween(2, 6))
						.build();

				double totalOrder = order.getPrice() + lineitem.getPrice() * lineitem.getQty();
				order.setPrice(totalOrder);
				order.addLineitem(lineitem);
			});
			jparepo.save(order);

		});

	}

}