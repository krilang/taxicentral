package no.ntnu.item.ttm4115.termassignment.chatmqtt;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.mqtt.basicmqtt.BasicMQTT.AdvancedConfiguration;

public class ChatMQTT extends Block {

	public AdvancedConfiguration createAdv(String topic) {
		return new AdvancedConfiguration(topic, 99);
	}

}
