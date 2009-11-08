package org.localstorm.cache.invalidator.dsl

import org.localstorm.cache.invalidator.Invalidator

public class ProcessingEngine {
	List<Rule> rules
	Invalidator inv
	
	public ProcessingEngine(Invalidator inv, List<Rule> rules) {
		this.rules = rules
		this.inv = inv
	}

	public void process(String resourceName,
						Map<String, String> attributes) {
		//println "Processing: "+resourceName+", attr="+attributes

		boolean fired = false
		for (Rule rule: rules) {
			if (!resourceName.equals(rule.getTrigger().getRes().getName())) {
				continue
			}

			fired = true
			List<DropAction> drops = rule.getDrops()
			List<CauseAction> causes = rule.getCauses()

			for (DropAction d: drops) {
				inv.invalidate(d.getCacheName(), extractValues(d.getRefs(), attributes))
			}

			for (CauseAction c: causes) {
				process(c.getChange().getRes().getName(), extractValues(c.getRefs(), attributes))
			}
		}

		if (!fired) {
			inv.invalidate(resourceName, attributes)
		}
	}

	public void printRules() {
		for (Rule r: rules)	{
			println "Rule: " + r.getTrigger() + " {"
			for (DropAction da : r.getDrops()) {
				println "\t" + da
			}
			for (CauseAction ca : r.getCauses()) {
				println "\t" + ca
			}
			println "}"
		}
	}

	
	private Map<String, String> extractValues(Map<String, ParamReference> refs, Map<String, String> scope)
	{
		Map<String, String> values = new LinkedHashMap()
		for (Map.Entry<String, ParamReference> entry: refs)	{
			values.put(entry.key, entry.value.getParamValue(scope))
		}
		return values;
	}
}