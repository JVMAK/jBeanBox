package examples.example8_benchmark;

import com.github.drinkjava2.BeanBoxContext;

import examples.example8_benchmark.objects.A;
import examples.example8_benchmark.objects.B;

/**
 * This example is similar like example5, but in "TesterBox" use Java type safe configurations to create bean instance.
 * <br/>
 * 
 * @author Yong Zhu
 * @since 2016-8-23
 */

public class Tester {

	public static class AA {
		AA(A a, B b) {
		}

		public void print() {
			System.out.println("AA");
		}
	}

	public static void main(String[] args) {
		long repeattimes = 10000;
		System.out.printf("BenchMark Test, build Object tree %s times\r\n\r\n", repeattimes);
		A aold = null, a = null;

		// #1 use config1, normal configuration
		BeanBoxContext ctx = new BeanBoxContext(BeanBoxConfig1.class).setIgnoreAnnotation(true);
		aold = ctx.getBean(A.class);
		long start = System.currentTimeMillis();
		for (int i = 0; i < repeattimes; i++) {
			a = ctx.getBean(A.class);
		}
		long end = System.currentTimeMillis();
		String type = (aold == a) ? "(Singlton)" : "(Prototype)";
		System.out.println(String.format("%45s|%6sms", "BeanBox Normal Configuration" + type, end - start));

		// #2 use config2, java type safe configuration
		ctx = new BeanBoxContext(BeanBoxConfig2.class).setIgnoreAnnotation(true);
		aold = ctx.getBean(A.class);
		start = System.currentTimeMillis();
		for (int i = 0; i < repeattimes; i++) {
			a = ctx.getBean(A.class);
		}
		end = System.currentTimeMillis();
		type = singltTonORPrototype(aold, a);
		System.out.println(String.format("%45s|%6sms", "BeanBox Type Safe Configuration" + type, end - start));

		// #3 use config3, only annotations
		ctx = new BeanBoxContext();
		aold = ctx.getBean(A.class);
		start = System.currentTimeMillis();
		for (int i = 0; i < repeattimes; i++) {
			a = ctx.getBean(A.class);
		}
		end = System.currentTimeMillis();
		type = singltTonORPrototype(aold, a);
		System.out.println(String.format("%45s|%6sms", "BeanBox Annotation Only" + type, end - start));

		// #4 use config1, normal configuration
		ctx = new BeanBoxContext(BeanBoxConfig1.class).setIgnoreAnnotation(true);
		aold = ctx.getSingletonBean(A.class);// force return a singleton bean
		start = System.currentTimeMillis();
		for (int i = 0; i < repeattimes; i++) {
			a = ctx.getSingletonBean(A.class);
		}
		end = System.currentTimeMillis();
		type = singltTonORPrototype(aold, a);
		System.out.println(String.format("%45s|%6sms", "BeanBox Normal Configuration" + type, end - start));

		// #5 use config2, java type safe configuration
		ctx = new BeanBoxContext(BeanBoxConfig2.class).setIgnoreAnnotation(true);
		aold = ctx.getSingletonBean(A.class); // force return a singleton bean
		start = System.currentTimeMillis();
		for (int i = 0; i < repeattimes; i++) {
			a = ctx.getSingletonBean(A.class);
		}
		end = System.currentTimeMillis();
		type = singltTonORPrototype(aold, a);
		System.out.println(String.format("%45s|%6sms", "BeanBox Type Safe Configuration" + type, end - start));

		// #6 use config3, only annotations
		ctx = new BeanBoxContext();
		aold = ctx.getSingletonBean(A.class);// force return a singleton bean
		start = System.currentTimeMillis();
		for (int i = 0; i < repeattimes; i++) {
			a = ctx.getSingletonBean(A.class);
		}
		end = System.currentTimeMillis();
		type = singltTonORPrototype(aold, a);
		System.out.println(String.format("%45s|%6sms", "BeanBox Annotation Only" + type, end - start));

	}

	private static String singltTonORPrototype(A aold, A a) {
		String type;
		type = (aold == a) ? "(Singlton)" : "(Prototype)";
		return type;
	}
}