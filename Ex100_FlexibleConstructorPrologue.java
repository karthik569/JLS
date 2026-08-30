package jls;

import java.util.Objects;

/**
 * JLS §8.8.7 (Java 23/24 Preview - JEP 482 / JEP 492): Flexible Constructor Bodies & Early Construction Context
 * 
 * In standard Java (1.0 to 21), an explicit constructor invocation (super(...) or this(...))
 * had to be the strictly FIRST statement in a constructor body.
 * 
 * JEP 492 introduces "Flexible Constructor Bodies", splitting a constructor into:
 * 1. Prologue (Early Construction Context): Statements that execute BEFORE super(...) or this(...).
 * 2. Explicit Constructor Invocation: The super(...) or this(...) call.
 * 3. Epilogue: Statements that execute AFTER the super/this invocation.
 * 
 * Compilation:
 *     javac --release 24 --enable-preview Ex100_FlexibleConstructorPrologue.java
 *     java --enable-preview jls.Ex100_FlexibleConstructorPrologue
 * 
 * Specification Rules & Safety Guarantees:
 * 1. Early Construction Context: In the prologue, the instance is under early construction.
 *    - Allowed: Parameter validation, argument preparation, complex calculations, and initializing
 *      fields of the class being created BEFORE super().
 *    - Prohibited (JLS §8.8.7.1): Reading fields before assignment, invoking instance methods on 'this',
 *      or letting 'this' escape into other objects before super() completes.
 * 2. Solving the "Superclass Overriding Callback" Bug:
 *    If a superclass constructor invokes an overridden method, subclass fields initialized in the prologue
 *    are guaranteed to be populated, preventing null/zero bugs!
 */
public class Ex100_FlexibleConstructorPrologue {

    public static void main(String[] args) {
        System.out.println("=== JLS §8.8.7: Flexible Constructor Bodies (JEP 492) Demo ===\n");
        
        System.out.println("1. Instantiating SubClass with Prologue Field Initialization & Validation:");
        SubClass sub = new SubClass("User-9988", 42);
        System.out.println("   SubClass created: " + sub);
        
        System.out.println("\n2. Testing Validation Failure Before Superclass Initialization:");
        try {
            new SubClass(null, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("   Caught expected validation error in prologue: " + e.getMessage());
            System.out.println("   (Superclass constructor was never invoked, saving resources!)");
        }
    }
}

// Superclass
class BaseClass {
    private final String id;

    public BaseClass(String id) {
        System.out.println("     -> [BaseClass Constructor] Initializing Base with id: " + id);
        this.id = id;
        // Superclass hook
        onBaseInitialized();
    }

    protected void onBaseInitialized() {
        System.out.println("     -> [BaseClass] onBaseInitialized hook called.");
    }

    @Override
    public String toString() {
        return "BaseClass[id=" + id + "]";
    }
}

// Subclass demonstrating Flexible Constructor Body Prologue
class SubClass extends BaseClass {
    private final int calculatedScore;
    private final String sanitizedTag;

    public SubClass(String rawTag, int multiplier) {
        // =========================================================================
        // PROLOGUE (Early Construction Context - Executed BEFORE super(...))
        // =========================================================================
        // 1. Argument validation:
        if (rawTag == null || rawTag.isBlank()) {
            throw new IllegalArgumentException("Tag must not be null or blank");
        }
        
        // 2. Preprocessing / transformations:
        String cleaned = rawTag.trim().toLowerCase();
        int score = multiplier * 100;
        
        // 3. Initializing subclass fields prior to super():
        this.calculatedScore = score;
        this.sanitizedTag = cleaned;
        
        // =========================================================================
        // EXPLICIT CONSTRUCTOR INVOCATION
        // =========================================================================
        super("TAG_" + cleaned);
        
        // =========================================================================
        // EPILOGUE (Normal Construction Context - Executed AFTER super(...))
        // =========================================================================
        System.out.println("     -> [SubClass Constructor] Epilogue completed.");
    }

    @Override
    protected void onBaseInitialized() {
        // Because fields were initialized in the prologue, calculatedScore is ALREADY 4200 (not 0)!
        System.out.println("     -> [SubClass Overridden Hook] calculatedScore is safely accessible: " + this.calculatedScore);
    }

    @Override
    public String toString() {
        return "SubClass[sanitizedTag=" + sanitizedTag + ", score=" + calculatedScore + ", " + super.toString() + "]";
    }
}
