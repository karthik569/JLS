# Java Language Specification (JLS) Examples Directory

This directory contains 50 compilable Java programs (`Ex01` through `Ex50` plus core JLS foundation demos) demonstrating important Java Language Specifications and language evolution from **Java 8 through Java 25**.

Each program includes inline source code comments referencing exact JLS sections (`§`) and explaining the underlying behavior enforced by the compiler and runtime.

---

## Index of Examples

| # | File | Min Java Version | JLS Specification & Description |
|---|---|---|---|
| -- | [`TypeConversionsDemo.java`](file:///sdcard/Download/termux/JLS/TypeConversionsDemo.java) | Java 8+ | **JLS §5**: Widening/narrowing conversions, boxing/unboxing, binary numeric promotion |
| -- | [`PolymorphismAndResolutionDemo.java`](file:///sdcard/Download/termux/JLS/PolymorphismAndResolutionDemo.java) | Java 8+ | **JLS §8, §15.12**: Dynamic method selection, field hiding, static method hiding, overload resolution |
| -- | [`MemoryModelDemo.java`](file:///sdcard/Download/termux/JLS/MemoryModelDemo.java) | Java 8+ | **JLS §17.4**: Java Memory Model (JMM), volatile visibility, happens-before ordering |
| -- | [`GenericsAndErasureDemo.java`](file:///sdcard/Download/termux/JLS/GenericsAndErasureDemo.java) | Java 8+ | **JLS §4.5, §4.6, §4.10**: Type erasure, PECS wildcards, array covariance vs generic invariance |
| -- | [`GenericInterfaceDemo.java`](file:///sdcard/Download/termux/JLS/GenericInterfaceDemo.java) | Java 8+ | **JLS §9.1.2**: Generic Interfaces & Type Parameterization |
| -- | [`ControlFlowAndDefiniteAssignmentDemo.java`](file:///sdcard/Download/termux/JLS/ControlFlowAndDefiniteAssignmentDemo.java) | Java 17+ | **JLS §14.21, §16**: Definite assignment rules, unreachable statements, sealed switch |
| 01 | [`Ex01_LambdaExpressions.java`](file:///sdcard/Download/termux/JLS/Ex01_LambdaExpressions.java) | Java 8 | **JLS §15.27**: Lambda Expressions, Functional Interfaces & Target Typing |
| 02 | [`Ex02_MethodReferences.java`](file:///sdcard/Download/termux/JLS/Ex02_MethodReferences.java) | Java 8 | **JLS §15.13**: Method References (Static, Instance, Constructor) |
| 03 | [`Ex03_DefaultAndStaticInterfaceMethods.java`](file:///sdcard/Download/termux/JLS/Ex03_DefaultAndStaticInterfaceMethods.java) | Java 8 | **JLS §9.4**: Default and Static Methods in Interfaces |
| 04 | [`Ex04_TypeAndRepeatingAnnotations.java`](file:///sdcard/Download/termux/JLS/Ex04_TypeAndRepeatingAnnotations.java) | Java 8 | **JLS §9.7.4 & §9.6.3**: Type Annotations & Repeating Annotations |
| 05 | [`Ex05_TargetTypeInference.java`](file:///sdcard/Download/termux/JLS/Ex05_TargetTypeInference.java) | Java 8 | **JLS §15.12.2.8**: Enhanced Target Type Inference |
| 06 | [`Ex06_TryWithResourcesEnhancement.java`](file:///sdcard/Download/termux/JLS/Ex06_TryWithResourcesEnhancement.java) | Java 9 | **JLS §14.20.3**: Effectively Final Variables in Try-With-Resources |
| 07 | [`Ex07_PrivateInterfaceMethods.java`](file:///sdcard/Download/termux/JLS/Ex07_PrivateInterfaceMethods.java) | Java 9 | **JLS §9.4**: Private Instance & Static Interface Methods |
| 08 | [`Ex08_AnonymousClassDiamond.java`](file:///sdcard/Download/termux/JLS/Ex08_AnonymousClassDiamond.java) | Java 9 | **JLS §15.9.5**: Anonymous Class Creation with Diamond `<>` Operator |
| 09 | [`Ex09_SafeVarargsPrivateMethods.java`](file:///sdcard/Download/termux/JLS/Ex09_SafeVarargsPrivateMethods.java) | Java 9 | **JLS §9.6.4.5**: `@SafeVarargs` Annotation on Private Instance Methods |
| 10 | [`Ex10_LocalVariableTypeInference.java`](file:///sdcard/Download/termux/JLS/Ex10_LocalVariableTypeInference.java) | Java 10 | **JLS §14.4**: Local Variable Type Inference (`var`) & Non-Denotable Types |
| 11 | [`Ex11_VarInLambdaParameters.java`](file:///sdcard/Download/termux/JLS/Ex11_VarInLambdaParameters.java) | Java 11 | **JLS §15.27.1**: `var` Syntax in Implicit Lambda Parameters |
| 12 | [`Ex12_NestBasedAccessControl.java`](file:///sdcard/Download/termux/JLS/Ex12_NestBasedAccessControl.java) | Java 11 | **JLS §11.1.1**: Nestmate NestHost and NestMembers Access Control |
| 13 | [`Ex13_SwitchExpressionsAndYield.java`](file:///sdcard/Download/termux/JLS/Ex13_SwitchExpressionsAndYield.java) | Java 14 | **JLS §14.11 & §14.21**: Switch Expressions & `yield` Statements |
| 14 | [`Ex14_TextBlocks.java`](file:///sdcard/Download/termux/JLS/Ex14_TextBlocks.java) | Java 15 | **JLS §3.10.6 & §3.10.7**: Text Blocks, String Indentation & Escapes (`\s`, `\`) |
| 15 | [`Ex15_PatternMatchingInstanceof.java`](file:///sdcard/Download/termux/JLS/Ex15_PatternMatchingInstanceof.java) | Java 16 | **JLS §14.30.1**: Pattern Matching for `instanceof` & Flow Scoping |
| 16 | [`Ex16_RecordClasses.java`](file:///sdcard/Download/termux/JLS/Ex16_RecordClasses.java) | Java 16 | **JLS §8.10**: Record Classes & Compact Constructors |
| 17 | [`Ex17_SealedClasses.java`](file:///sdcard/Download/termux/JLS/Ex17_SealedClasses.java) | Java 17 | **JLS §8.1.6**: Sealed Classes/Interfaces, `permits`, `final`, `non-sealed` |
| 18 | [`Ex18_PatternMatchingSwitch.java`](file:///sdcard/Download/termux/JLS/Ex18_PatternMatchingSwitch.java) | Java 21 | **JLS §14.11**: Switch Pattern Matching, Guarded Clauses (`when`) & Nulls |
| 19 | [`Ex19_RecordPatterns.java`](file:///sdcard/Download/termux/JLS/Ex19_RecordPatterns.java) | Java 21 | **JLS §14.30.3**: Record Deconstruction Patterns & Nested Patterns |
| 20 | [`Ex20_VirtualThreads.java`](file:///sdcard/Download/termux/JLS/Ex20_VirtualThreads.java) | Java 21 | **JLS §17**: Virtual Threads Execution Model (`Thread.ofVirtual()`) |
| 21 | [`Ex21_UnnamedVariablesAndPatterns.java`](file:///sdcard/Download/termux/JLS/Ex21_UnnamedVariablesAndPatterns.java) | Java 22 | **JLS §6.1 & §14.30**: Unnamed Variables & Record Patterns (`_`) |
| 22 | [`Ex22_StatementsBeforeSuper.java`](file:///sdcard/Download/termux/JLS/Ex22_StatementsBeforeSuper.java) | Java 22+ | **JLS §8.8.7.1**: Statements Permitted Before `super(...)` in Constructors |
| 23 | [`Ex23_HexFormatAndIntegerLiterals.java`](file:///sdcard/Download/termux/JLS/Ex23_HexFormatAndIntegerLiterals.java) | Java 17+ | **JLS §3.10.1**: Underscore Integer Literals & `HexFormat` Utility |
| 24 | [`Ex24_SequencedCollections.java`](file:///sdcard/Download/termux/JLS/Ex24_SequencedCollections.java) | Java 21 | **JLS §8.1.5**: Sequenced Collections & Reversible Ordering |
| 25 | [`Ex25_StructuredConcurrency.java`](file:///sdcard/Download/termux/JLS/Ex25_StructuredConcurrency.java) | Java 21+ | **JLS §17**: Structured Concurrency Subtask Scopes |
| 26 | [`Ex26_ScopedValues.java`](file:///sdcard/Download/termux/JLS/Ex26_ScopedValues.java) | Java 21+ | **JLS §17**: Scoped Values Context Sharing |
| 27 | [`Ex27_ForeignMemoryAPI.java`](file:///sdcard/Download/termux/JLS/Ex27_ForeignMemoryAPI.java) | Java 22 | **JLS §4.1**: Foreign Function & Off-Heap Memory Segment API |
| 28 | [`Ex28_StreamGatherers.java`](file:///sdcard/Download/termux/JLS/Ex28_StreamGatherers.java) | Java 22+ | **JLS §15.12**: Stream Gatherers Intermediate Windowing Operations |
| 29 | [`Ex29_CompactMainMethods.java`](file:///sdcard/Download/termux/JLS/Ex29_CompactMainMethods.java) | Java 23+ | **JLS §7.3**: Implicitly Declared Classes & Compact Main Methods |
| 30 | [`Ex30_PrimitiveTypePatterns.java`](file:///sdcard/Download/termux/JLS/Ex30_PrimitiveTypePatterns.java) | Java 23+ | **JLS §14.30.2**: Primitive Type Patterns in Switch |
| 31 | [`Ex31_FlexibleConstructorBodies.java`](file:///sdcard/Download/termux/JLS/Ex31_FlexibleConstructorBodies.java) | Java 23+ | **JLS §8.8.7**: Field Assignments Allowed Prior to Super Call |
| 32 | [`Ex32_ParallelArraysSorting.java`](file:///sdcard/Download/termux/JLS/Ex32_ParallelArraysSorting.java) | Java 8+ | **JLS §15.12 & §17**: Parallel Array Sorting & Fork-Join Execution |
| 33 | [`Ex33_OptionalTypeSafety.java`](file:///sdcard/Download/termux/JLS/Ex33_OptionalTypeSafety.java) | Java 8+ | **JLS §4.3**: Optional Type-Safe Monadic Operations |
| 34 | [`Ex34_CompletableFuturePipelines.java`](file:///sdcard/Download/termux/JLS/Ex34_CompletableFuturePipelines.java) | Java 8+ | **JLS §17.4**: Asynchronous Reactive Pipelines & Memory Visibility |
| 35 | [`Ex35_StreamTakeWhileDropWhile.java`](file:///sdcard/Download/termux/JLS/Ex35_StreamTakeWhileDropWhile.java) | Java 9+ | **JLS §15.12**: Stream `takeWhile` and `dropWhile` Boundary Semantics |
| 36 | [`Ex36_ImmutableCollectionFactories.java`](file:///sdcard/Download/termux/JLS/Ex36_ImmutableCollectionFactories.java) | Java 9+ | **JLS §4.3.3**: Unmodifiable Collection Factory Methods (`List.of`, `Set.of`) |
| 37 | [`Ex37_CollectorsUnmodifiable.java`](file:///sdcard/Download/termux/JLS/Ex37_CollectorsUnmodifiable.java) | Java 10+ | **JLS §4.3.3**: Stream `Collectors.toUnmodifiableList()` Guarantees |
| 38 | [`Ex38_CollectorsTeeing.java`](file:///sdcard/Download/termux/JLS/Ex38_CollectorsTeeing.java) | Java 12+ | **JLS §15.12**: Collector Merging with `Collectors.teeing()` |
| 39 | [`Ex39_StringAndFileUtilities.java`](file:///sdcard/Download/termux/JLS/Ex39_StringAndFileUtilities.java) | Java 12+ | **JLS §3.10.5**: String Transformations (`indent`, `transform`) |
| 40 | [`Ex40_StreamToList.java`](file:///sdcard/Download/termux/JLS/Ex40_StreamToList.java) | Java 16+ | **JLS §15.12**: Efficient Direct Stream Collection via `Stream.toList()` |
| 41 | [`Ex41_ObjectsValidation.java`](file:///sdcard/Download/termux/JLS/Ex41_ObjectsValidation.java) | Java 9+ | **JLS §4.3**: Null Defensiveness with `Objects.requireNonNullElse()` |
| 42 | [`Ex42_FlowReactiveStreams.java`](file:///sdcard/Download/termux/JLS/Ex42_FlowReactiveStreams.java) | Java 9+ | **JLS §17**: `java.util.concurrent.Flow` Reactive Stream Specification |
| 43 | [`Ex43_CleanerVsFinalization.java`](file:///sdcard/Download/termux/JLS/Ex43_CleanerVsFinalization.java) | Java 9+ | **JLS §12.6**: Memory Resource Cleaning using `Cleaner` vs Finalization |
| 44 | [`Ex44_DateTimeImmutability.java`](file:///sdcard/Download/termux/JLS/Ex44_DateTimeImmutability.java) | Java 8+ | **JLS §4.3**: Thread-Safe Immutability in `java.time` (`Instant`, `Duration`) |
| 45 | [`Ex45_MethodHandlesDynamicInvocation.java`](file:///sdcard/Download/termux/JLS/Ex45_MethodHandlesDynamicInvocation.java) | Java 7/8+ | **JLS §15.12**: Polymorphic Signature Invocations with `MethodHandle` |
| 46 | [`Ex46_VarHandleAtomicOperations.java`](file:///sdcard/Download/termux/JLS/Ex46_VarHandleAtomicOperations.java) | Java 9+ | **JLS §17.4**: Memory Fences & Atomic Operations with `VarHandle` |
| 47 | [`Ex47_LongAdderHighConcurrency.java`](file:///sdcard/Download/termux/JLS/Ex47_LongAdderHighConcurrency.java) | Java 8+ | **JLS §17**: Thread-Striped Atomic Cell Accumulation (`LongAdder`) |
| 48 | [`Ex48_StampedLockOptimisticRead.java`](file:///sdcard/Download/termux/JLS/Ex48_StampedLockOptimisticRead.java) | Java 8+ | **JLS §17.4**: Non-blocking Optimistic Read Validation with `StampedLock` |
| 49 | [`Ex49_CustomSpliterator.java`](file:///sdcard/Download/termux/JLS/Ex49_CustomSpliterator.java) | Java 8+ | **JLS §15.12**: Data Source Partitioning & Spliterator Traversal Rules |
| 50 | [`Ex50_StandardHttpClient.java`](file:///sdcard/Download/termux/JLS/Ex50_StandardHttpClient.java) | Java 11+ | **JLS §4.3**: Non-blocking Asynchronous HTTP Client Model |
| 51 | [`Ex51_LexicalStructure.java`](file:///sdcard/Download/termux/JLS/Ex51_LexicalStructure.java) | Java 8+ | **JLS §3**: Identifiers, Keywords, Literals & Lexical Structure |
| 52 | [`Ex52_TypesValuesVariables.java`](file:///sdcard/Download/termux/JLS/Ex52_TypesValuesVariables.java) | Java 8+ | **JLS §4**: Primitive/Reference Types, Type Erasure & Subtyping |
| 53 | [`Ex53_ConversionsAndContexts.java`](file:///sdcard/Download/termux/JLS/Ex53_ConversionsAndContexts.java) | Java 8+ | **JLS §5**: Primitive/Reference Conversions, Boxing & Contexts |
| 54 | [`Ex54_NamesAndPackages.java`](file:///sdcard/Download/termux/JLS/Ex54_NamesAndPackages.java) | Java 8+ | **JLS §6, §7**: Names, Scopes, Access Control & Packages |
| 55 | [`Ex55_ClassesDeepDive.java`](file:///sdcard/Download/termux/JLS/Ex55_ClassesDeepDive.java) | Java 17+ | **JLS §8**: Class Declarations, Members, Sealed Classes & Records |
| 56 | [`Ex56_InterfacesDeepDive.java`](file:///sdcard/Download/termux/JLS/Ex56_InterfacesDeepDive.java) | Java 9+ | **JLS §9**: Interface Members, Default/Private Methods & Annotations |
| 57 | [`Ex57_ArraysDeepDive.java`](file:///sdcard/Download/termux/JLS/Ex57_ArraysDeepDive.java) | Java 8+ | **JLS §10**: Array Creation, Access, Covariance & Class Objects |
| 58 | [`Ex58_ExceptionsDeepDive.java`](file:///sdcard/Download/termux/JLS/Ex58_ExceptionsDeepDive.java) | Java 9+ | **JLS §11**: Exception Hierarchy, Try-Catch-Finally & Chaining |
| 59 | [`Ex59_ExecutionAndInitialization.java`](file:///sdcard/Download/termux/JLS/Ex59_ExecutionAndInitialization.java) | Java 8+ | **JLS §12**: Class Loading, Static/Instance Initialization & VM Startup |
| 60 | [`Ex60_StatementsDeepDive.java`](file:///sdcard/Download/termux/JLS/Ex60_StatementsDeepDive.java) | Java 21+ | **JLS §14**: Control Flow, Switch Expressions & Pattern Matching |
| 61 | [`Ex61_ExpressionsDeepDive.java`](file:///sdcard/Download/termux/JLS/Ex61_ExpressionsDeepDive.java) | Java 8+ | **JLS §15**: Expression Evaluation, Method Resolution & Lambdas |
| 62 | [`Ex62_ThreadsAndLocksDeepDive.java`](file:///sdcard/Download/termux/JLS/Ex62_ThreadsAndLocksDeepDive.java) | Java 21+ | **JLS §17**: Synchronization, Memory Model & Virtual Threads |
| 63 | [`Ex63_TypeInferenceDeepDive.java`](file:///sdcard/Download/termux/JLS/Ex63_TypeInferenceDeepDive.java) | Java 11+ | **JLS §18**: Type Inference, Diamond Operator & Local Variable Type Inference |
| 64 | [`Ex64_GenericsWildcardsAndVariance.java`](file:///sdcard/Download/termux/JLS/Ex64_GenericsWildcardsAndVariance.java) | Java 8+ | **JLS §4.5.3, §4.5.4**: Generic Variance, PECS (Producer Extends, Consumer Super) & Wildcards |
| 65 | [`Ex65_EnumDeepDive.java`](file:///sdcard/Download/termux/JLS/Ex65_EnumDeepDive.java) | Java 8+ | **JLS §8.9**: Advanced Enums, Constant-Specific Class Bodies, EnumSet & EnumMap |
| 66 | [`Ex66_ReflectionAndDynamicProxies.java`](file:///sdcard/Download/termux/JLS/Ex66_ReflectionAndDynamicProxies.java) | Java 8+ | **Reflection API**: Dynamic Proxies, InvocationHandlers & Runtime Class Inspection |
| 67 | [`Ex67_OverloadingAndOverridingEdgeCases.java`](file:///sdcard/Download/termux/JLS/Ex67_OverloadingAndOverridingEdgeCases.java) | Java 8+ | **JLS §8.4, §15.12**: Covariant Returns, Overload Resolution Priority & Bridge Methods |
| 68 | [`Ex68_ClassLoadingAndInitialization.java`](file:///sdcard/Download/termux/JLS/Ex68_ClassLoadingAndInitialization.java) | Java 8+ | **JLS §12.4**: Static Initialization Order, Triggering & Lazy Loading |


---

## Compilation & Execution Guide

### Standard Compilation (Java 8 - 21)
To compile and execute standard JLS features up to Java 21:

```bash
# Compile specific example
javac --release 21 JLS/Ex19_RecordPatterns.java

# Run example
java jls.Ex19_RecordPatterns
```

### Preview Features (Java 22 - 25)
To compile and run examples using Java 22+ preview features (e.g. Statements Before Super, Stream Gatherers, Primitive Patterns):

```bash
# Compile preview examples
javac --release 25 --enable-preview JLS/Ex22_StatementsBeforeSuper.java

# Run preview example
java --enable-preview jls.Ex22_StatementsBeforeSuper
```
