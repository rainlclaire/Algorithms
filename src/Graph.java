import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 *
 *
 * ****** Usage *******
 * << Section 1. Set Up the Graph >>>
 *      • create a Graph with a call to
 *          new Graph()
 *
 *      • add a CEO to the graph:
 *          CEO is automatically created and added to the graph,
 *          you only need to add employees other than CEO
 *
 *      • add an employee, call:
 *          add_employee(id, boss_id, influence);
 *
 *      • You have to call process_graph() after you finished adding employees
 *
 * << Section 2. Get the result >>
 *     call:
 *          optimal_total_influence(k);
 *              k is the number of employees you want to influence.
 */
public class Graph {

    public ArrayList<Employee> all_employees;
    public PriorityQueue queue;

    public Graph(){
        queue = new PriorityQueue();
        all_employees = new ArrayList<Employee>();
        Employee ceo = new Employee(0, 0, null);
        all_employees.add(ceo);
    }


    /* add employee with given id, boss id and influence value*/
    public void add_employee(int id, int boss_id, int influence){
        /* find the boss */
        Employee boss;
        if (boss_id == 0)
            boss = all_employees.get(0);
        else
            boss = find_employee_with_id(boss_id);
        if (boss == null){
            System.err.printf("Error: boss %d not found\n", boss_id);
            System.exit(-1);
        }
        Employee employee = new Employee(id, influence, boss);
        all_employees.add(employee);
    }


    /**the optimal solution for influence k employee in g*/
    public int optimal_total_influence(int k){
        int optimal_result = 0;

        /*if k >0, do */

        while (k > 0){
            /* the heap_extract_max method keep track update for current max*/
            optimal_result += queue.heap_extract_max();
            k--;
        }
        return optimal_result;
    }


    public Employee find_employee_with_id(int id){
        Employee result = null;
        for (Employee e: all_employees){
            if (e.id() == id)
                result = e;
        }

        return result;
    }

    /**process_graph: which insert all leaf to queue*/
    public void process_graph(){
        /* find end nodes and insert them to priority queue */
        for (Employee e: all_employees){
            if (!e.has_sub())
                queue.insert(e);
        }
    }


    /*main funcition to run the problem*/
    public static void main(String args[]){

        Graph g = new Graph();

        Scanner sc = new Scanner(System.in);
        String pattern = "\\d*";
        int lines_of_inputs = sc.nextInt();
        int k = sc.nextInt();

        int employee_id, boss_id, influence;

        while (lines_of_inputs > 0){
            lines_of_inputs--;
            employee_id = sc.nextInt();
            boss_id = sc.nextInt();
            influence = sc.nextInt();
            g.add_employee(employee_id, boss_id, influence);
        }
        g.process_graph();

        System.out.println(g.optimal_total_influence(k));

    }
}
