package play.learn.java;

/**
 * https://www.youtube.com/watch?v=2Df1sDAyRvQ
 * https://en.wikipedia.org/wiki/Bayes%27_theorem
 */
public class BayesTheorem {

	public static void main(String[] args) {
		
		// assumption based on what we already know.
		
		//P(D) = 0.5      // probability democrat get elected
		//P(R) = 0.5      // probability republican get elected
		float prob_dem_won = 0.5f;
		float prob_rep_won = 0.5f;

		//P(a|D) = 0.25   // probability of elimination of tax given democrat is elected.
		//P(a'|D) = 0.75  // probability of non tax elimination given democrated is elected. a is also known as prime.
		float prob_tax_dem = 0.25f;
		float prob_ntax_dem = 0.75f;
		
		//P(a|R) = 0.85   // probability of elimination of tax given republican is elected.
		//P(a'|R) = 0.15  // probability of non tax elimination given republican is elected. a is also known as prime.
		float prob_tax_rep = 0.85f;
		float prob_ntax_rep = 0.15f;
		
		// ---to the real work ----
		
		// total probability given a tax is cut
		//P(a) is the probability that taxes will be cut after the election.
		// P(a) = P(R) x P(a|R) + P(D) * P(a|D)
		// = (0.5 x 0.85) + (0.5 * 0.25)
		// = 0.55
		
		float prob_tax = prob_rep_won * prob_tax_rep + prob_dem_won * prob_tax_dem;
		System.out.println("probability given a tax is cut " +prob_tax);
		
		// total probability given a tax is not cut

        // P(a') = P(a'|D) x P(D) + P('a|R) x P(R)
        //         = (0.75 x 0.5) + (0.15 x 0.5)
        //         = 0.375 + 0.075
        //         = 0.45
        // or simple way
        //         = 1 - P(a)
        //         = 0.45
		float prob_ntax = prob_ntax_dem * prob_dem_won + prob_ntax_rep * prob_rep_won;
		System.out.println("probability given a tax is NOT cut " + prob_ntax);
		

       // given bayes theorem, will give what you want to know.
		

		// the probability the democrat elected given tax cut is happened.
		// P(D|a) = P(a|D) x P(D) / P(a)
		//            = 0.25 x 0.5 / 0.55
		//            = 0.227 (or 22.7%)
		float p_dem_tax = prob_tax_dem * prob_dem_won / prob_tax;
		System.out.println("probability democrat elected given tax is cut " + p_dem_tax);
       
		// the probability the republican elected given tax cut is happened.
		// P(R|a) = P(a|R) x P(R) / P(a)
		// = 0.85 x 0.5 / 0.55
		// = 0.773 (or 77.3%)
		float p_rep_tax = prob_tax_rep * prob_rep_won / prob_tax;
		System.out.println("probability democrat elected given tax is NOT cut " + p_rep_tax);

	}

}
