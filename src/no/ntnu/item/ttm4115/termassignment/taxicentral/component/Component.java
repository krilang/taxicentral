package no.ntnu.item.ttm4115.termassignment.taxicentral.component;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.mqtt.basicmqtt.BasicMQTT.AdvancedConfiguration;

public class Component extends Block {

	public AdvancedConfiguration createAdv() {
		return new AdvancedConfiguration("central", 99);
	}

}
