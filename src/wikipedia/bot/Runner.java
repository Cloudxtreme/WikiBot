package wikipedia.bot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import wikipedia.bot.parsers.CommonsDistrictFlagsParser;
import wikipedia.bot.parsers.GeneralInformationDistrictFlagsParser;
import wikipedia.bot.parsers.HeraldryDistrictFlagsParser;
import wikipedia.bot.parsers.SiteParser;
import wikipedia.bot.parsers.WikipediaDistrictFlagsParser;
import wikipedia.utils.ResourceUtils;
import wikipedia.utils.Wikifier;

/**
 * TODO
 * 1. create programming in templates
 * 2. special object for selectors
 * 3. command-line arguments as in JCommander
 * 
 * @author Mir4ik
 * @version 0.1 15.02.2014
 */
public class Runner {

	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			System.err.println("Command-line arguments was not found!");
			System.exit(1);
		}
		String task = args[0];
		if (task == null || task.isEmpty()) {
			System.err.println("Wrong command-line arguments!");
			System.exit(1);
		}
		Target t = null;
		try {
			t = Target.create(task);
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
		String nameDistrict = t.getTaskParam(0);
		String nameTranslDistrict = t.getTaskParam(1);
		String heraldryLink = t.getTaskParam(2);
		String nameOblast = t.getTaskParam(3);
		String templateFile = t.getTaskParam(4);
		String resultFile = t.getTaskParam(5);
		String template = null;
		try {
			template = ResourceUtils.readFile(templateFile, "UTF-8");
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
		SiteParser[] parsers = {
			new GeneralInformationDistrictFlagsParser(),
			new HeraldryDistrictFlagsParser(),
			new CommonsDistrictFlagsParser(),
			new WikipediaDistrictFlagsParser()
		};
		String[] selectors = {
			nameDistrict,
			nameTranslDistrict,
			nameOblast,
			heraldryLink
		};
		Map<String, String> params = new HashMap<String, String>();
		try {
			for (int i = 0; i < parsers.length; i++) {
				params.putAll(parsers[i].parse(selectors));
			}
		} catch (ApplicationException e) {
			System.err.println(e);
			System.exit(1);
		}
		WikiString result = new WikiString(template, params);
		result.setNULLStrategy();
		String wikifiedResult = Wikifier.wikify(result);
		try {
			ResourceUtils.writeFile(resultFile, wikifiedResult, "UTF-8");
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
		System.out.println("Succesfully created!");
	}
}