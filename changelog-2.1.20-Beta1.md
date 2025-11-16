## 2.1.20-Beta1

### Analysis API. Light Classes
- [KT-71781](https://youtrack.jetbrains.com/issue/KT-71781) SLC: migrate SLC from KotlinModificationTrackerService to KotlinModificationTrackerFactory

### Analysis API. Providers and Caches
- [KT-72390](https://youtrack.jetbrains.com/issue/KT-72390) Kotlin project full of red code

### Analysis API. Surface
- [KT-72075](https://youtrack.jetbrains.com/issue/KT-72075) `defaultType` should be available for `KaClassifierSymbol` instead of `KaNamedClassSymbol`

### Backend. Wasm
- [KT-72223](https://youtrack.jetbrains.com/issue/KT-72223) Compiler generates an invalid glue-code for externals with backquoted identifiers

### Docs & Examples
- [KT-68693](https://youtrack.jetbrains.com/issue/KT-68693) [Docs] Add documentation of how to use power-assert with Maven

### Frontend. Checkers
- [KT-72040](https://youtrack.jetbrains.com/issue/KT-72040) Extra checkers: false-positive unused parameter warnings on anonymous lambda parameters
- [KT-72041](https://youtrack.jetbrains.com/issue/KT-72041) Extra checkers: false-positive unused parameter warnings on implicit lambda parameters
- [KT-72340](https://youtrack.jetbrains.com/issue/KT-72340) K1/K2 difference in de-duplication of OPT_IN_USAGE and OPT_IN_TO_INHERITANCE
- [KT-72618](https://youtrack.jetbrains.com/issue/KT-72618) Cannot define operator inc/dec in class context
- [KT-72725](https://youtrack.jetbrains.com/issue/KT-72725) KMP: Unsupported actualization of inherited java field in expect class
- [KT-72338](https://youtrack.jetbrains.com/issue/KT-72338) OPT_IN_TO_INHERITANCE suggests to annotate a final classifier with SubclassOptInRequired

### Frontend. FIR tree
- [KT-70854](https://youtrack.jetbrains.com/issue/KT-70854) K2 IDE: annotation on delegation causes illegal argument exception

### Frontend. FIR2IR
- [KT-71961](https://youtrack.jetbrains.com/issue/KT-71961) K2 debugger evaluation ClassCastException in IrElementsCreationUtilsKt#createFilesWithBuiltinsSyntheticDeclarationsIfNeeded
- [KT-73399](https://youtrack.jetbrains.com/issue/KT-73399) compile-time JVM codegen failure on a KProperty argument of a KSuspendFunction parameter

### Frontend. Resolution and Inference
- [KT-71753](https://youtrack.jetbrains.com/issue/KT-71753) PCLA: false-negative operator ambiguity error on fixing a type variable on demand for an operator assignment
- [KT-72996](https://youtrack.jetbrains.com/issue/KT-72996) false-positive unresolved reference error on an overloaded callable reference in a lambda return position on the left-hand size of an elvis operator
- [KT-73051](https://youtrack.jetbrains.com/issue/KT-73051) incorrect direction of subtyping violation in type mismatch error's message for A<X<C>> </: A<Y<Tv>> given a Tv <: Rv == C constraint from a lambda return position
- [KT-69985](https://youtrack.jetbrains.com/issue/KT-69985) K2: simple classifier names from root package are resolved without imports in non-root packages

### IR. Inlining
- [KT-72912](https://youtrack.jetbrains.com/issue/KT-72912) Rewrite `andAllOuterClasses` located in `FunctionInlining`
- [KT-67220](https://youtrack.jetbrains.com/issue/KT-67220) Drop caching of deserialized/lowered inline functions

### Klibs
- [KT-71633](https://youtrack.jetbrains.com/issue/KT-71633) [2.1.0] Suspicious "Argument type mismatch" error

### Libraries
- [KT-73291](https://youtrack.jetbrains.com/issue/KT-73291) Uuid.random() requires security context in WasmJs
- [KT-31880](https://youtrack.jetbrains.com/issue/KT-31880) UUID functionality to fix Java bugs as well as extend it

### Native
- [KT-74377](https://youtrack.jetbrains.com/issue/KT-74377) Kotlin Native: release executable crashes with error 139 

### Tools. Gradle
- [KT-72303](https://youtrack.jetbrains.com/issue/KT-72303) KGP 2.1.0-Beta2 broke compatibility with KSP
- [KT-62273](https://youtrack.jetbrains.com/issue/KT-62273) Use new FUS plugin in Kotlin 
- [KT-74846](https://youtrack.jetbrains.com/issue/KT-74846) Gradle Configuration Cache miss on second build with 2.1.20-Beta2
- [KT-73782](https://youtrack.jetbrains.com/issue/KT-73782) KGP diagnostics reporter: emojis added to KGP warning/errors are displayed broken on Windows 
- [KT-74394](https://youtrack.jetbrains.com/issue/KT-74394) KGP + isolated projects: "Something has been appended to this collector already"

### Tools. Incremental Compile
- [KT-69333](https://youtrack.jetbrains.com/issue/KT-69333) Remove built-in ABI snapshot implementation

